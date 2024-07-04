package com.project.weatherwear.user.dto;

import lombok.Data;

@Data
public class RequestModifyPWDTO {
    private String password;
    private String confirmPassword;
}
