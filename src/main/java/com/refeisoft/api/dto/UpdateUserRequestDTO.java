package com.refeisoft.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern.Flag;

public record UpdateUserRequestDTO(
        @NotBlank(message = "Nome não pode ficar em branco.")
        String name,

        @NotBlank(message = "Email não pode ficar em branco.")
        @Email(flags = Flag.CASE_INSENSITIVE, regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email em formato inválido.")
        String email
        ) {
}
