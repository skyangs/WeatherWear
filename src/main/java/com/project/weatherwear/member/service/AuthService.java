package com.project.weatherwear.member.service;

import com.project.weatherwear.member.domain.User;
import com.project.weatherwear.member.dto.RequestEmailCheckDTO;
import com.project.weatherwear.member.dto.RequestRegisterUserDTO;
import com.project.weatherwear.member.dto.ResponseCommonDTO;
import com.project.weatherwear.member.dto.ResponseEmailCheckDTO;
import com.project.weatherwear.member.mapper.UserMapper;
import com.project.weatherwear.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseCommonDTO registerUser(RequestRegisterUserDTO requestRegisterUserDTO){

        User user = UserMapper.INSTANCE.toUser(requestRegisterUserDTO);
        if(requestRegisterUserDTO.isSocial()){
            user.isSocialLogin();
        }

        if(!requestRegisterUserDTO.isSocial()){
            user.isRegularLogin();
        }

        userRepository.save(user);

        return new ResponseCommonDTO(true, "User registered successfully.");

    }

    public ResponseEmailCheckDTO checkDuplicateEmail(String email){

        boolean isAvailable = !userRepository.findByEmail(email);
        String message = isAvailable ? "This email is available." : "This email is unavailable.";

        return new ResponseEmailCheckDTO(isAvailable, message);

    }
}
