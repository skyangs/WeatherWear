package com.project.weatherwear.member.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RequestRegisterUserDTO {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String password;

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;

    @NotBlank(message = "닉네임은 필수입니다.")
    private final String nickname;

    @AssertTrue(message = "필수 약관에 항상 동의해야합니다")
    private final boolean agreeToTerms;

    private final boolean isSocial;

}
