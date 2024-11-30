package com.refeisoft.domain.repository;

import com.refeisoft.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = """
            SELECT (COUNT(user) > 0) FROM User user WHERE user.email = :email
            """)
    boolean existsByEmail(@Param("email") String email);
}
