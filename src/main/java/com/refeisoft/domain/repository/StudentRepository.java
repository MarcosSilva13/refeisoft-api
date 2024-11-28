package com.refeisoft.domain.repository;

import com.refeisoft.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @Query(value = """
            SELECT (COUNT(student) > 0) FROM Student student WHERE student.registrationNumber = :registrationNumber
            """)
    boolean existsByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    @Query(value = """
            SELECT (COUNT(student) > 0) FROM Student student WHERE student.email = :email
            """)
    boolean existsByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE Student student SET student.status = 'ACTIVE'
            WHERE student.status = 'BLOCKED' AND student.blockedUntil <= :date
            """)
    public void unblockStudents(@Param("date") LocalDate date);
}
