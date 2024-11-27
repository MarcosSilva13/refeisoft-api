package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsumeCreditRequestDTO(
        @NotBlank(message = "Número de matrícula não pode ficar em branco.")
        String registrationNumber,

        @NotNull(message = "Tipo de refeição não pode ficar sem valor.")
        MealType mealType
) {
}
