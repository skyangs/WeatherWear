package com.project.weatherwear.user.dto;

import lombok.Getter;

@Getter
public class ResponseFindEmailDTO {

    private final String email;

    public ResponseFindEmailDTO(String email){
        this.email = email;
    }
}
