package com.refeisoft.domain.service;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.TransactionResponseDTO;
import com.refeisoft.api.filter.TransactionFilter;
import com.refeisoft.api.mapper.TransactionMapper;
import com.refeisoft.domain.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private static final int PAGE_SIZE = 10;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public PageResponseDTO getAllTransaction(int page, TransactionFilter filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "transactionId");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<TransactionResponseDTO> transactionPage = transactionRepository.findAll(filter.toTransactionSpecification(), pageRequest)
                .map(transactionMapper::toTransactionResponseDTO);

        return new PageResponseDTO(transactionPage.getContent(), transactionPage.getTotalPages());
    }
}
