package com.refeisoft.api.mapper;

import com.refeisoft.api.dto.TransactionResponseDTO;
import com.refeisoft.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    @Mapping(source = "student.studentId", target = "studentId")
    TransactionResponseDTO toTransactionResponseDTO(Transaction transaction);
}
