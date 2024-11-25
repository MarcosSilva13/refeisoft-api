package com.refeisoft.domain.specification;

import com.refeisoft.domain.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class StudentSpecs {
    private StudentSpecs() {}

    public static Specification<Student> byStudentName(String name) {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            if (ObjectUtils.isEmpty(name)) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }
}
