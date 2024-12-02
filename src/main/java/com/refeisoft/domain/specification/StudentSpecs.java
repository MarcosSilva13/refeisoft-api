package com.refeisoft.domain.specification;

import com.refeisoft.domain.entity.Student;
import com.refeisoft.domain.enums.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class StudentSpecs {
    private StudentSpecs() {}

    public static Specification<Student> byStudentName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(name)) {
                return null;
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Student> byStudentStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(status)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
