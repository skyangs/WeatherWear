package com.project.weatherwear.domain.oauth2;

import lombok.Data;

@Data
public class Profile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private Boolean is_default_image;
    private Boolean is_default_nickname;
}