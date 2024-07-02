package com.project.weatherwear.user.controller;

import com.project.weatherwear.user.dto.*;
import com.project.weatherwear.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/users")
@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseCommonDTO> signup(@Validated @RequestBody RequestRegisterUserDTO registerUserDTO){
        return ResponseEntity.ok(userService.registerUser(registerUserDTO));
    }

    @PostMapping("/email-check")
    public ResponseEntity<ResponseDuplicateCheckDTO> checkDuplicateEmail(@Validated @RequestBody RequestEmailCheckDTO emailCheckDTO){
        return ResponseEntity.ok(userService.checkDuplicateEmail(emailCheckDTO.getEmail()));
    }

    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<ResponseDuplicateCheckDTO> checkDuplicateNickname(@PathVariable("nickname") String nickname){
        return ResponseEntity.ok(userService.checkDuplicateNickname(nickname));
    }

    @PostMapping("/email")
    public ResponseEntity<ResponseFindEmailDTO> findEmail(@Validated @RequestBody RequestFindEmailDTO findEmailDTO){
        return ResponseEntity.ok(userService.findEmail(findEmailDTO.getName(), findEmailDTO.getNickname()));
    }

    @PostMapping("/password")
    public ResponseEntity<ResponseCommonDTO> findPassword(@Validated @RequestBody RequestFindPWDTO findPWDTO){
        return ResponseEntity.ok(userService.findPassword(findPWDTO.getEmail(), findPWDTO.getName(), findPWDTO.getNickname()));
    }



}
