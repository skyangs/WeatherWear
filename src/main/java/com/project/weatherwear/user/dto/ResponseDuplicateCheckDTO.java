package com.project.weatherwear.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDuplicateCheckDTO {

    private final boolean isAvailable;
    private final String message;

}
