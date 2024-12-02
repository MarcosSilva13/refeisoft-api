package com.refeisoft.api.filter;

import com.refeisoft.domain.entity.Student;
import com.refeisoft.domain.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import static com.refeisoft.domain.specification.StudentSpecs.byStudentName;
import static com.refeisoft.domain.specification.StudentSpecs.byStudentStatus;

public record StudentFilter(String name, Status status) {

    public Specification<Student> toStudentSpecification() {
        return byStudentName(name)
                .and(byStudentStatus(status));
    }
}
