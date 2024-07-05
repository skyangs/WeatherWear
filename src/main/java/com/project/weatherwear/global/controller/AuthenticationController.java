package com.project.weatherwear.global.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.weatherwear.user.dto.UserDTO;
import com.project.weatherwear.global.dto.oauth2.kakao.KakaoOAuthToken;
import com.project.weatherwear.global.dto.oauth2.kakao.KakaoProfile;
import com.project.weatherwear.global.util.JWTUtil;
import com.project.weatherwear.global.service.CustomUserDetailsService;
import com.project.weatherwear.user.service.UserService;
import com.project.weatherwear.user.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


/**
 * 카카오 로그인 관련
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {

    private final JWTUtil jwtUtil;

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/api/auth")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String authTestController() {
        return "index";
    }

    /**
     * 카카오 로그인버튼을 누르면 계정정보를 바탕으로 accessCode를 얻고 code를 다시 카카오서버로 보내 사용자의 프로필을 얻어옴.
     * 만약 사용자가 DB에 저장되어 있지 않다면 response에 프로필정보를 담아서 보내고 redirect시킴
     * 사용자가 DB에 저장되어 있다면 jwt Token 발급.
     *
     * @param code          카카오 계정관련된 코드
     * @param response
     * @return
     */
    @GetMapping("/api/v1/auth/kakao-login")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        try {

            // 1. Authorization Code를 통해 Access Token 요청
            RestTemplate rt1 = new RestTemplate();
            HttpHeaders headers1 = new HttpHeaders();

            String tokenUri = "https://kauth.kakao.com/oauth/token";
            headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            //body
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "dd48ec8f82facf5b6bc667227e05721b");
            params.add("redirect_uri", "http://localhost:8080/api/v1/auth/kakao-login");
            params.add("code", code);
            params.add("client_secret", "DbGEVNtFOR3XDAZK7m6OcGJvbqC0O277");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers1);
            ResponseEntity<KakaoOAuthToken> kakaoResponse = rt1.postForEntity(tokenUri, request, KakaoOAuthToken.class);

            KakaoOAuthToken kakaoOAuthToken = kakaoResponse.getBody();


            // 2. Access Token을 통해 사용자 정보 요청
            String userInfoUri = "https://kapi.kakao.com/v2/user/me";

            RestTemplate rt2 = new RestTemplate();
            HttpHeaders headers2 = new HttpHeaders();

            headers2.setBearerAuth(kakaoOAuthToken.getAccess_token());
            headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> kakaoInfo = new HttpEntity<>(headers2);


            ResponseEntity<KakaoProfile> userInfoResponse = rt2.exchange(userInfoUri, HttpMethod.POST, kakaoInfo, KakaoProfile.class);

            KakaoProfile kakaoProfile = userInfoResponse.getBody();
            log.info(kakaoProfile);

            //3. 사용자 정보를 통해 JWT 생성 및 로그인 처리
            String username = kakaoProfile.getId() + kakaoProfile.getKakao_account().getProfile().getNickname();
            if (username == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile not provided by Kakao");
            }

            Optional<User> existUser = userService.findByUsername(username);// 사용자 정보 저장 또는 생성

            ObjectMapper objectMapper = new ObjectMapper();
            if (existUser.isEmpty()) {//기존에 카카오로 회원가입한 사용자가 아니라면 추가 정보 입력하는 페이지로 이동함
                //OAuth2 토큰으로 받은 kakaoProfile
                response.getWriter().write(objectMapper.writeValueAsString(kakaoProfile));
                response.sendRedirect("/");
            } else {//기존에 카카오로 로그인한 사용자라면 jwt 토큰을 발급하고 메인 페이지로 리다이렉트
                UserDTO userDTO = (UserDTO) userDetailsService.loadUserByUsername(existUser.get().getUsername());

                String accessToken = jwtUtil.generateToken(userDTO, 60);
                String refreshToken = jwtUtil.generateToken(userDTO, 60 * 24);

                Map<String, Object> claims = userDTO.getClaims();

                claims.put("accessToken", accessToken);
                claims.put("refreshToken", refreshToken);


                // 해쉬맵인 claims를 JSON 문자열로 변환
                String jsonString = objectMapper.writeValueAsString(claims);

                Cookie cookie = new Cookie("auth_token", jsonString);
                cookie.setHttpOnly(true);
                //cookie.setSecure(true); // HTTPS 환경에서만 사용
                cookie.setPath("/");
                cookie.setMaxAge((int) (24 * 60 * 60)); // 만료시간 설정 24시간

                response.addCookie(cookie);

                response.setStatus(HttpStatus.CREATED.value());
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(claims));
                response.getWriter().close();

                response.sendRedirect("/home");

                return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(claims));
            }
            return null;
        } catch (Exception e) {
            log.error("Kakao login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Kakao login failed");
        }
    }
}

