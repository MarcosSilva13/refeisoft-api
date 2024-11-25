package com.refeisoft.domain.repository;

import com.refeisoft.domain.entity.Credit;
import com.refeisoft.domain.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByStudentId(Long studentId);

    Optional<Credit> findByStudentIdAndMealType(Long studentId, MealType mealType);

    Optional<Credit> findByStudentRegistrationNumberAndMealType(String registrationNumber, MealType mealType);
}
