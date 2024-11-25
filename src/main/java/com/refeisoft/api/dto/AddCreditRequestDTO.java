package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;

public record AddCreditRequestDTO(
        Long studentId,
        MealType mealType,
        Integer creditQuantity) {
}
