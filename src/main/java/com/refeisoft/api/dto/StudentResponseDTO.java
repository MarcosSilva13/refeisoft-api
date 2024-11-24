package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.Status;

import java.time.LocalDate;

public record StudentResponseDTO(Long studentId, String name, String registrationNumber, String email, Status status,
                                 LocalDate blockedUntil) {
}
