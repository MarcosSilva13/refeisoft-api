package com.refeisoft.api.mapper;

import com.refeisoft.api.dto.TransactionResponseDTO;
import com.refeisoft.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    TransactionResponseDTO toTransactionResponseDTO(Transaction transaction);
}
