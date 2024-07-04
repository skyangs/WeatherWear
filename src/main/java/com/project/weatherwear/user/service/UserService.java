package com.project.weatherwear.user.service;


import com.project.weatherwear.user.entity.User;

import java.util.Optional;

public interface UserService {


    Optional<User> findByUsername(String username);

    void updateUser(String username, String newNickname, String newName);

    void changePassword(String username, String newPassword);

    void changeNickname(String username, String nickname);
}