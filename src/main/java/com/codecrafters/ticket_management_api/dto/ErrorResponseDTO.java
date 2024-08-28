package com.codecrafters.ticket_management_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseDTO {
    private List<String> errors;

    public ErrorResponseDTO(List<String> errors) {
        this.errors = errors;
    }

}