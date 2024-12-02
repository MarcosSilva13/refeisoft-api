package com.refeisoft.api.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordRequesDTO(
        @NotBlank(message = "Senha atual não pode ficar em branco.")
        String currentPassword,

        @NotBlank(message = "Nova senha não pode ficar em branco.")
        String newPassword) {
}
