package com.refeisoft.api.mapper;

import com.refeisoft.api.dto.CreditResponseDTO;
import com.refeisoft.domain.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditMapper {

    @Mapping(source = "student.studentId", target = "studentId")
    CreditResponseDTO toCreditResponseDTO(Credit credit);
}
