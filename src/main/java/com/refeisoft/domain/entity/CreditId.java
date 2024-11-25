package com.refeisoft.domain.entity;

import com.refeisoft.domain.enums.MealType;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CreditId implements Serializable {

    private Long studentId;
    private MealType mealType;

    public CreditId() {
    }

    public CreditId(Long studentId, MealType mealType) {
        this.studentId = studentId;
        this.mealType = mealType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CreditId creditId)) return false;
        return Objects.equals(studentId, creditId.studentId) && mealType == creditId.mealType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, mealType);
    }
}
