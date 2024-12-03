package com.refeisoft.api.controller;

import com.refeisoft.api.dto.ConsumeCreditRequestDTO;
import com.refeisoft.api.dto.CreditRequestDTO;
import com.refeisoft.api.dto.CreditResponseDTO;
import com.refeisoft.api.openapi.CreditControllerDoc;
import com.refeisoft.domain.service.CreditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditController implements CreditControllerDoc {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<CreditResponseDTO>> getAll(@PathVariable Long studentId) {
        List<CreditResponseDTO> creditResponseDTOS = creditService.getAllCredits(studentId);
        return ResponseEntity.ok(creditResponseDTOS);
    }

    @PostMapping("/addition")
    public ResponseEntity<CreditResponseDTO> addCredit(@RequestBody @Valid CreditRequestDTO requestDTO) {
        CreditResponseDTO creditResponseDTO = creditService.addCredit(requestDTO);
        return ResponseEntity.ok(creditResponseDTO);
    }

    @PostMapping("/consume")
    public ResponseEntity<CreditResponseDTO> consumeCredit(@RequestBody @Valid ConsumeCreditRequestDTO requestDTO) {
        CreditResponseDTO creditResponseDTO = creditService.consumeCredit(requestDTO);
        return ResponseEntity.ok(creditResponseDTO);
    }
}
