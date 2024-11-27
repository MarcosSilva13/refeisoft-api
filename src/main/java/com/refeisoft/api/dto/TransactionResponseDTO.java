package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.MealType;
import com.refeisoft.domain.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long transactionId,
        MealType mealType,
        TransactionType transactionType,
        Integer quantity,
        LocalDateTime transactionDate,
        Long studentId
) {
}
