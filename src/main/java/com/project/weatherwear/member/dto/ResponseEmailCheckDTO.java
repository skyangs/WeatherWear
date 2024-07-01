package com.project.weatherwear.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseEmailCheckDTO {

    private final boolean isAvailable;
    private final String message;

}
