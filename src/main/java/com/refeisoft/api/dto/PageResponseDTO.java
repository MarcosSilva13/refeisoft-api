package com.refeisoft.api.dto;

import java.util.List;

public record PageResponseDTO(List<?> content, int totalPages) {
}
