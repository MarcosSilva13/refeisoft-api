package com.refeisoft.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refeisoft.api.dto.ErrorDTO;
import com.refeisoft.domain.entity.User;
import com.refeisoft.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    public SecurityFilter(UserRepository userRepository, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenJwt = recoverToken(request);
        try {
            if (tokenJwt != null) {
                String email = jwtTokenService.getTokenSubject(tokenJwt);
                authenticateUser(email);
            }
        } catch (RuntimeException ex) {
            ErrorDTO errorDTO = new ErrorDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();

            response.setStatus(errorDTO.status());
            response.setContentType("application/json");
            response.getWriter().println(objectMapper.writeValueAsString(errorDTO));
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userOptional.get(),
                    null, userOptional.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null ? header.replace("Bearer ", "") : null;
    }
}
