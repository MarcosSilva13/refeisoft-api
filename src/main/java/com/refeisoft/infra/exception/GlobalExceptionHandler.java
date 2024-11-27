package com.refeisoft.infra.exception;

import com.refeisoft.api.dto.ErrorDTO;
import com.refeisoft.api.dto.ErrorFieldsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> entityNotFoundExceptionHandler(EntityNotFoundException ex) {
        return generateErrorDTO(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateAttributeException.class)
    public ResponseEntity<ErrorDTO> duplicateAttributeExceptionHandler(DuplicateAttributeException ex) {
        return generateErrorDTO(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CreditQuantityException.class)
    public ResponseEntity<ErrorDTO> creditQuantityExceptionHandler(CreditQuantityException ex) {
        return generateErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex) {
        logger.info("\nErro no servidor: " + ex.getMessage(), ex);
        return generateErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no servidor, contate os desenvolvedores.");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> fieldsMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        ErrorFieldsDTO errorFieldsDTO = new ErrorFieldsDTO(status.value(), "Dado(s) não preenchido(s) corretamente.", fieldsMessage);

        return ResponseEntity.status(errorFieldsDTO.status()).body(errorFieldsDTO);
    }

    private ResponseEntity<ErrorDTO> generateErrorDTO(HttpStatus status, String message) {
        ErrorDTO errorDTO = new ErrorDTO(status.value(), message);
        return ResponseEntity.status(errorDTO.status()).body(errorDTO);
    }
}
