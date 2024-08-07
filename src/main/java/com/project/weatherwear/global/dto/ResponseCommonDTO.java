package com.project.weatherwear.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseCommonDTO {
    private boolean success;
    private String message;
}
