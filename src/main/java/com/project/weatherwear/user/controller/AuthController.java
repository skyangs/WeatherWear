package com.project.weatherwear.user.controller;

import com.project.weatherwear.user.dto.RequestEmailCheckDTO;
import com.project.weatherwear.user.dto.RequestRegisterUserDTO;
import com.project.weatherwear.user.dto.ResponseCommonDTO;
import com.project.weatherwear.user.dto.ResponseDuplicateCheckDTO;
import com.project.weatherwear.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/users")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseCommonDTO> signup(@Validated @RequestBody RequestRegisterUserDTO registerUserDTO){
        return ResponseEntity.ok(authService.registerUser(registerUserDTO));
    }

    @PostMapping("/email-check")
    public ResponseEntity<ResponseDuplicateCheckDTO> checkDuplicateEmail(@Validated @RequestBody RequestEmailCheckDTO emailCheckDTO){
        return ResponseEntity.ok(authService.checkDuplicateEmail(emailCheckDTO.getEmail()));
    }

    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<ResponseDuplicateCheckDTO> checkDuplicateNickname(@PathVariable("nickname") String nickname){
        return ResponseEntity.ok(authService.checkDuplicateNickname(nickname));
    }


}
