package com.project.weatherwear.controller;

import com.project.weatherwear.domain.dto.*;
import com.project.weatherwear.domain.entity.User;
import com.project.weatherwear.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseUserDetailsDTO> getUserDetails(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> result = userService.findByUsername(username);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        User user = result.get();

        log.info(authentication);
        log.info(user);

        ResponseUserDetailsDTO responseUserDetailsDTO = ResponseUserDetailsDTO.builder()
                .email(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .temperature(0)
                .isSocial(user.isSocial())
                .build();

        return ResponseEntity.ok().body(responseUserDetailsDTO);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseCommonDTO> updateUserDetails(RequestModifyUserDTO requestModifyUserDTO, Authentication authentication) {
        String username = authentication.getName();
        userService.updateUser(username, requestModifyUserDTO.getNickname(), requestModifyUserDTO.getName());

        return ResponseEntity.ok().body(new ResponseCommonDTO(true, "Profile updated successfully."));
    }

    @PutMapping("/me/password")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseCommonDTO> changePassword(RequestModifyPWDTO requestModifyPWDTO, Authentication authentication) {
        if (!requestModifyPWDTO.getPassword().equals(requestModifyPWDTO.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ResponseCommonDTO(false, "Passwords do not match"));
        }
        String username = authentication.getName();
        Optional<User> result = userService.findByUsername(username);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseCommonDTO(false, "User not found"));
        }

        User user = result.get();

        userService.changePassword(user.getUsername(), requestModifyPWDTO.getPassword());

        return ResponseEntity.ok().body(new ResponseCommonDTO(true, "Password updated successfully."));
    }

    @PutMapping("/update-nickname")
    public ResponseEntity<ResponseCommonDTO> changeNickname(@RequestParam String nickname, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> result = userService.findByUsername(username);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseCommonDTO(false, "User not found"));
        }

        userService.changeNickname(username, nickname);

        return ResponseEntity.ok().body(new ResponseCommonDTO(true, "Nickname updated successfully."));
    }
}