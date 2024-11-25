package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;

import java.time.LocalDateTime;

public record CreditResponseDTO(Long studentId, MealType mealType, Integer creditQuantity, LocalDateTime lastUpdate) {
}
