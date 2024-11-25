package com.refeisoft.api.controller;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.filter.TransactionFilter;
import com.refeisoft.domain.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO> getAll(@RequestParam int page, TransactionFilter filter) {
        PageResponseDTO pageResponseDTO = transactionService.getAllTransaction(page, filter);
        return ResponseEntity.ok(pageResponseDTO);
    }
}
