package com.refeisoft.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BlockStudentRequestDTO(
        @NotNull(message = "O aluno não pode ficar sem valor.")
        Long studentId,

        @NotNull(message = "Data de bloqueio não pode ficar sem valor.")
        @FutureOrPresent(message = "Data de bloqueio deve ser atual ou futura.")
        LocalDate blockUntil) {
}
