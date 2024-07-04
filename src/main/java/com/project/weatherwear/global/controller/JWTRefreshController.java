package com.project.weatherwear.global.controller;


import com.project.weatherwear.global.util.JWTUtil;
import com.project.weatherwear.global.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * JWT 재발급 관련
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class JWTRefreshController {
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    /**
     * accessToken은 Authrization Header에 존재하고 Refresh Token은 Param으로 받아옴
     * 만료되었다면 두개의 토큰 모두 재발급함.
     *
     * @param authHeader
     * @param refreshToken
     * @return
     */

    @PostMapping("refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestHeader("Authorization") String authHeader,
                                                                  @RequestParam("refreshToken") String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Refresh token is required"));
        }
        if(authHeader == null || authHeader.length() < 7) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Authorization Header  is required"));
        }

        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 유저 정보를 바탕으로 새로운 Access Token 발급
            String newAccessToken = jwtUtil.generateToken(userDetails, 60*24);
            String newRefreshToken = jwtUtil.generateToken(userDetails, 60*24*7);

            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("accessToken", newAccessToken);
            tokenMap.put("refreshToken", newRefreshToken);

            return ResponseEntity.ok(tokenMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
