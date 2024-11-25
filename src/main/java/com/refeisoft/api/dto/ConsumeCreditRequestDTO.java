package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;

public record ConsumeCreditRequestDTO(
        String registrationNumber,
        MealType mealType
) {
}
