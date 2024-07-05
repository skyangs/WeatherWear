package com.project.weatherwear.global.dto.oauth2.kakao;

import lombok.Data;

@Data
public class Profile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private Boolean is_default_image;
    private Boolean is_default_nickname;
}
