package com.refeisoft.domain.repository;

import com.refeisoft.domain.entity.Credit;
import com.refeisoft.domain.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query(value = """
            SELECT credit FROM Credit credit 
            WHERE credit.student.studentId = :studentId
            """)
    List<Credit> findAllByStudentId(@Param("studentId") Long studentId);

    @Query(value = """
            SELECT credit FROM Credit credit 
            WHERE credit.student.studentId = :studentId AND credit.mealType = :mealType
            """)
    Optional<Credit> findByStudentIdAndMealType(@Param("studentId") Long studentId, @Param("mealType") MealType mealType);

    @Query(value = """
            SELECT credit FROM Credit credit 
            WHERE credit.student.registrationNumber = :registrationNumber AND credit.mealType = :mealType
            """)
    Optional<Credit> findByStudentRegistrationNumberAndMealType(@Param("registrationNumber") String registrationNumber,
                                                                @Param("mealType") MealType mealType);
}
