package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;
import jakarta.validation.constraints.NotNull;

public record CreditRequestDTO(
        @NotNull(message = "O aluno não pode ficar sem valor.")
        Long studentId,

        @NotNull(message = "Tipo de refeição não pode ficar sem valor.")
        MealType mealType,

        @NotNull(message = "Quantidade de créditos não pode ficar sem valor.")
        Integer creditQuantity) {
}
