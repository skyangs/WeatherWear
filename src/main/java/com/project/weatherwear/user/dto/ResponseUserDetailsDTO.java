package com.project.weatherwear.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ResponseUserDetailsDTO {
   private String email;
    private String name;
    private String nickname;

    //  likedPosts
    //- myPosts : List<Post>
    //- followers : List<User>
    //- following : List<User>

    private float temperature;
    private boolean isSocial;
}
