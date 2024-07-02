package com.project.weatherwear.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseCommonDTO {

    private final boolean success;
    private final String message;

}
