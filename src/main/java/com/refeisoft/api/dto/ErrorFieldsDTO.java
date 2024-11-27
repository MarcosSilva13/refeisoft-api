package com.refeisoft.api.dto;

import java.util.List;

public record ErrorFieldsDTO(int status, String message, List<String> fieldsMessage) {
}
