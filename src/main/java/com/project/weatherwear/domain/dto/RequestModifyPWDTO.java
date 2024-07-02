package com.project.weatherwear.domain.dto;

import lombok.Data;

@Data
public class RequestModifyPWDTO {
    private String password;
    private String confirmPassword;
}
