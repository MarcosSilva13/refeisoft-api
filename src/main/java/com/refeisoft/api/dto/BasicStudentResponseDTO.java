package com.refeisoft.api.dto;

public record BasicStudentResponseDTO(
        Long studentId,
        String name,
        String registrationNumber
) {
}
