package com.refeisoft.domain.service;

import com.refeisoft.api.dto.ConsumeCreditRequestDTO;
import com.refeisoft.api.dto.CreditRequestDTO;
import com.refeisoft.api.dto.CreditResponseDTO;
import com.refeisoft.api.mapper.CreditMapper;
import com.refeisoft.domain.entity.Credit;
import com.refeisoft.domain.entity.Student;
import com.refeisoft.domain.entity.Transaction;
import com.refeisoft.domain.enums.MealType;
import com.refeisoft.domain.enums.TransactionType;
import com.refeisoft.domain.repository.CreditRepository;
import com.refeisoft.domain.repository.TransactionRepository;
import com.refeisoft.infra.exception.CreditQuantityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;
    private final TransactionRepository transactionRepository;
    private final CreditMapper creditMapper;
    private static final int CONSUMPTION_VALUE = 1;

    public CreditService(CreditRepository creditRepository, TransactionRepository transactionRepository,
                         CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.transactionRepository = transactionRepository;
        this.creditMapper = creditMapper;
    }

    public List<CreditResponseDTO> getAllCredits(Long studentId) {
        return creditRepository.findAllByStudentId(studentId).stream().map(creditMapper::toCreditResponseDTO).toList();
    }

    @Transactional
    public CreditResponseDTO addCredit(CreditRequestDTO requestDTO) {
        if (requestDTO.creditQuantity() < 1) {
            throw new CreditQuantityException("A quantidade de créditos não pode ser menor que 1.");
        }

        Credit credit = creditRepository.findByStudentIdAndMealType(requestDTO.studentId(), requestDTO.mealType())
                .orElseThrow(() -> new EntityNotFoundException("Dado do crédito não encontrado."));

        return updateCredit(credit, requestDTO.creditQuantity(), TransactionType.ADDITION);
    }

    @Transactional
    public CreditResponseDTO consumeCredit(ConsumeCreditRequestDTO requestDTO) {
        Credit credit = creditRepository.findByStudentRegistrationNumberAndMealType(requestDTO.registrationNumber(), requestDTO.mealType())
                .orElseThrow(() -> new EntityNotFoundException("Dado do crédito não encontrado."));

        if (credit.getCreditQuantity() <= 0) {
            throw new CreditQuantityException("Quantidade insuficiente de créditos para consumo.");
        }

        return updateCredit(credit, CONSUMPTION_VALUE, TransactionType.CONSUMPTION);
    }

    private CreditResponseDTO updateCredit(Credit credit, int quantity, TransactionType transactionType) {
        int newCreditQuantity = transactionType == TransactionType.ADDITION
                ? credit.getCreditQuantity() + quantity
                : credit.getCreditQuantity() - quantity;

        LocalDateTime dateTime = LocalDateTime.now();
        credit.setCreditQuantity(newCreditQuantity);
        credit.setLastUpdate(dateTime);

        updateTransaction(credit.getMealType(), transactionType, quantity, dateTime, credit.getStudent());
        return creditMapper.toCreditResponseDTO(credit);
    }

    private void updateTransaction(MealType mealType, TransactionType transactionType, int quantity,
                                   LocalDateTime dateTime, Student student) {
        Transaction transaction = new Transaction(mealType, transactionType, quantity, dateTime, student);
        transactionRepository.save(transaction);
    }
}
