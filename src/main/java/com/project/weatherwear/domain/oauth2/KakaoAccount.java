package com.project.weatherwear.domain.oauth2;

import lombok.Data;

@Data
public class KakaoAccount {
    private Boolean profile_needs_agreement;
    private Boolean profileNicknameNeedsAgreement;
    private Boolean profileImageNeedsAgreement;
    private Profile profile;
    private Boolean has_email;
    private Boolean email_needs_agreement;
    private Boolean has_gender;
    private Boolean gender_needs_agreement;
}
