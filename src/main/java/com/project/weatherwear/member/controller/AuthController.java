package com.project.weatherwear.member.controller;

import com.project.weatherwear.member.dto.RequestRegisterUserDTO;
import com.project.weatherwear.member.dto.ResponseCommonDTO;
import com.project.weatherwear.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/users")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseCommonDTO> signup(@Validated @RequestBody RequestRegisterUserDTO requestRegisterUserDTO){
        return ResponseEntity.ok(authService.registerUser(requestRegisterUserDTO));
    }
}