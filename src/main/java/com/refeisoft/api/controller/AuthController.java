package com.refeisoft.api.controller;

import com.refeisoft.api.dto.LoginRequestDTO;
import com.refeisoft.api.dto.TokenJwtDTO;
import com.refeisoft.api.dto.UserRequestDTO;
import com.refeisoft.api.dto.UserResponseDTO;
import com.refeisoft.api.openapi.AuthControllerDoc;
import com.refeisoft.domain.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserResponseDTO userResponseDTO = authService.registerUser(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJwtDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        TokenJwtDTO tokenJwtDTO = authService.login(requestDTO);
        return ResponseEntity.ok(tokenJwtDTO);
    }
}
