package com.refeisoft.domain.entity;

import com.refeisoft.domain.enums.MealType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_credit")
@IdClass(CreditId.class)
public class Credit {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Id
    @Column(name = "meal_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(name = "credit_quantity", nullable = false)
    private Integer creditQuantity;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    public Credit() {
    }

    public Credit(Student student, MealType mealType, Integer creditQuantity, LocalDateTime lastUpdate) {
        this.student = student;
        this.mealType = mealType;
        this.creditQuantity = creditQuantity;
        this.lastUpdate = lastUpdate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public Integer getCreditQuantity() {
        return creditQuantity;
    }

    public void setCreditQuantity(Integer creditQuantity) {
        this.creditQuantity = creditQuantity;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Credit credit)) return false;
        return Objects.equals(student, credit.student) && mealType == credit.mealType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, mealType);
    }
}
