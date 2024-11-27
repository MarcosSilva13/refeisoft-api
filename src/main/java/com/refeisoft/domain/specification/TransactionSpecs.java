package com.refeisoft.domain.specification;

import com.refeisoft.domain.entity.Student;
import com.refeisoft.domain.entity.Transaction;
import com.refeisoft.domain.enums.MealType;
import com.refeisoft.domain.enums.TransactionType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public class TransactionSpecs {
    private TransactionSpecs() {}

    private static final String STUDENT = "student";

    public static Specification<Transaction> byStudentName(String studentName) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(studentName)) {
                return null;
            }
            Join<Transaction, Student> studentJoin = root.join(STUDENT, JoinType.INNER);
            return criteriaBuilder.like(studentJoin.get("name"), "%" + studentName + "%");
        };
    }

    public static Specification<Transaction> byMealType(MealType mealType) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(mealType)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("mealType"), mealType);
        };
    }

    public static Specification<Transaction> byTransactionType(TransactionType transactionType) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(transactionType)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("transactionType"), transactionType);
        };
    }

    public static Specification<Transaction> byTransactionDate(LocalDateTime initialDate, LocalDateTime finalDate) {
        return (root, query, criteriaBuilder) -> {
            if (!ObjectUtils.isEmpty(initialDate) && !ObjectUtils.isEmpty(finalDate)) {
                return criteriaBuilder.between(root.get("transactionDate"), initialDate, finalDate);
            } else if (!ObjectUtils.isEmpty(initialDate)) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), initialDate);
            } else if (!ObjectUtils.isEmpty(finalDate)) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), finalDate);
            } else {
                return null;
            }
        };
    }
}
