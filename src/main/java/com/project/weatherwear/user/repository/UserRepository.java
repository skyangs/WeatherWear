package com.project.weatherwear.user.repository;

import com.project.weatherwear.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean findByEmail(String email);
    boolean findByNickname(String nickname);
}
