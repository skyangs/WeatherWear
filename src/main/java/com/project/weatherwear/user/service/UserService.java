package com.project.weatherwear.user.service;

import com.project.weatherwear.common.exception.Exception;
import com.project.weatherwear.user.domain.User;
import com.project.weatherwear.user.dto.RequestRegisterUserDTO;
import com.project.weatherwear.user.dto.ResponseCommonDTO;
import com.project.weatherwear.user.dto.ResponseDuplicateCheckDTO;
import com.project.weatherwear.user.dto.ResponseFindEmailDTO;
import com.project.weatherwear.user.mapper.UserMapper;
import com.project.weatherwear.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.weatherwear.common.exception.ErrorCode.NOT_EXIST_EMAIL;
import static com.project.weatherwear.common.exception.ErrorCode.NOT_EXIST_USER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

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

    public ResponseDuplicateCheckDTO checkDuplicateEmail(String email){

        boolean isAvailable = !userRepository.findByEmail(email);
        String message = isAvailable ? "This email is available." : "This email is unavailable.";

        return new ResponseDuplicateCheckDTO(isAvailable, message);

    }

    public ResponseDuplicateCheckDTO checkDuplicateNickname(String nickname){

        boolean isAvailable = !userRepository.findByNickname(nickname);
        String message = isAvailable ? "This nickname is available." : "This nickname is unavailable.";

        return new ResponseDuplicateCheckDTO(isAvailable, message);

    }

    public ResponseFindEmailDTO findEmail(String name, String nickname){

        User user = userRepository.findByNameAndNickname(name, nickname)
                .orElseThrow(() -> new Exception(NOT_EXIST_EMAIL));

        return new ResponseFindEmailDTO(user.getEmail());

    }

    public ResponseCommonDTO findPassword(String email, String name, String nickname){

        User user = userRepository.findByEmailAndNameAndNickname(email, name, nickname)
                .orElseThrow(() -> new Exception(NOT_EXIST_USER));

        return new ResponseCommonDTO(true, "Email verification successful.");

    }
}
