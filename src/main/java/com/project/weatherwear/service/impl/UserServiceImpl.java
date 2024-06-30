package com.project.weatherwear.service.impl;

import com.project.weatherwear.domain.entity.UserEntity;
import com.project.weatherwear.repository.UserRepository;
import com.project.weatherwear.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserEntity findOrCreateUser(String username) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setIsSocial(true);
            newUser.setRole("ROLE_USER");
            return userRepository.save(newUser);
        });
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
