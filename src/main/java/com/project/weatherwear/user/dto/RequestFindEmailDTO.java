package com.project.weatherwear.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestFindEmailDTO {

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    private final String nickname;
}
