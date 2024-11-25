package com.refeisoft.domain.entity;

import com.refeisoft.domain.enums.MealType;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CreditId implements Serializable {

    private Student student;
    private MealType mealType;

    public CreditId() {
    }

    public CreditId(Student student, MealType mealType) {
        this.student = student;
        this.mealType = mealType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CreditId creditId)) return false;
        return Objects.equals(student, creditId.student) && mealType == creditId.mealType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, mealType);
    }
}
