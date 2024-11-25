package com.refeisoft.domain.repository;

import com.refeisoft.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @Query(value = """
            SELECT (COUNT(student) > 0) FROM Student student WHERE student.registrationNumber = :registrationNumber
            """)
    boolean existsByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    @Query(value = """
            SELECT (COUNT(student) > 0) FROM Student student WHERE student.email = :email
            """)
    boolean existsByEmail(@Param("email") String email);
}
