package com.refeisoft.api.filter;

import com.refeisoft.domain.entity.Transaction;
import com.refeisoft.domain.enums.MealType;
import com.refeisoft.domain.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static com.refeisoft.domain.specification.TransactionSpecs.*;

public record TransactionFilter(
        String studentName,
        MealType mealType,
        TransactionType transactionType,
        LocalDateTime initialDate,
        LocalDateTime finalDate) {

    public Specification<Transaction> toTransactionSpecification() {
        return byStudentName(studentName)
                .and(byMealType(mealType))
                .and(byTransactionType(transactionType))
                .and(byTransactionDate(initialDate, finalDate));
    }
}
