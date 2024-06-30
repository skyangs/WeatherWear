package com.project.weatherwear.repository;

import com.project.weatherwear.domain.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        UserEntity user = UserEntity.builder()
                .username("asdf")
                .password(passwordEncoder.encode("asdf"))
                .nickname("asdf")
                .isSocial(false)
                .build();

        userRepository.save(user);

    }

}