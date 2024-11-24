package com.refeisoft.api.dto;

import com.refeisoft.domain.enums.Status;

public record StudentRequestDTO(String name, String registrationNumber, String email) {
}
