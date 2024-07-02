package com.project.weatherwear.repository;

import com.project.weatherwear.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void insertUser(){
        User user = User.builder()
                .username("asdf1")
                .password(passwordEncoder.encode("asdf1"))
                .name("asdf1")
                .nickname("asdf1")
                .isSocial(false)
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }
}