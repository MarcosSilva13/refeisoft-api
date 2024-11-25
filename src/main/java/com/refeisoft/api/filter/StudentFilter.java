package com.refeisoft.api.filter;

import com.refeisoft.domain.entity.Student;
import org.springframework.data.jpa.domain.Specification;

import static com.refeisoft.domain.specification.StudentSpecs.byStudentName;

public record StudentFilter(String name) {

    public Specification<Student> toStudentSpecification() {
        return byStudentName(name);
    }
}
