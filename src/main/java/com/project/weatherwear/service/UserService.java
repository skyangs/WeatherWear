package com.project.weatherwear.service;


import com.project.weatherwear.domain.dto.UserDTO;
import com.project.weatherwear.domain.entity.UserEntity;
import com.project.weatherwear.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {


    UserEntity findOrCreateUser(String username) ;

    Optional< UserEntity> findByUsername(String username);
}