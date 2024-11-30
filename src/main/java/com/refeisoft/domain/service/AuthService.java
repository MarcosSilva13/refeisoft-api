package com.refeisoft.domain.service;

import com.refeisoft.api.dto.LoginRequestDTO;
import com.refeisoft.api.dto.TokenJwtDTO;
import com.refeisoft.api.dto.UserRequestDTO;
import com.refeisoft.api.dto.UserResponseDTO;
import com.refeisoft.api.mapper.UserMapper;
import com.refeisoft.domain.entity.User;
import com.refeisoft.domain.repository.UserRepository;
import com.refeisoft.infra.exception.DuplicateAttributeException;
import com.refeisoft.infra.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtTokenService jwtTokenService, UserMapper userMapper,
                       AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO requestDTO)  {
        if (userRepository.existsByEmail(requestDTO.email())) {
            throw new DuplicateAttributeException("O email informado já existe.");
        }

        User user = userMapper.toUserEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        User userCreated = userRepository.save(user);

        return userMapper.toUserResponseDTO(userCreated);
    }

    public TokenJwtDTO login(LoginRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());
        Authentication authenticate = authenticationManager.authenticate(usernamePassword);
        String tokenJwt = jwtTokenService.generateToken((User) authenticate.getPrincipal());

        return new TokenJwtDTO(tokenJwt);
    }
}
