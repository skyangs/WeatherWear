package com.project.weatherwear.domain.oauth2;

import lombok.Data;

import java.util.UUID;

@Data
public class KakaoProfile {
    private Long id;

    private String connected_at;

    private Properties properties;

    private KakaoAccount kakao_account;

    private String email;

    private UUID for_partner;
}