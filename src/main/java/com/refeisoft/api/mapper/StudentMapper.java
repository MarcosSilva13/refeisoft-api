package com.refeisoft.api.mapper;

import com.refeisoft.api.dto.StudentRequestDTO;
import com.refeisoft.api.dto.StudentResponseDTO;
import com.refeisoft.domain.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    Student toStudentEntity(StudentRequestDTO requestDTO);

    StudentResponseDTO toStudentResponseDTO(Student student);

    void toUpdateStudent(StudentRequestDTO requestDTO, @MappingTarget Student student);
}
