package com.project.weatherwear.user.repository;

import com.project.weatherwear.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean findByEmail(String email);
    boolean findByNickname(String nickname);

    Optional<User> findByNameAndNickname(String name, String nickname);
    Optional<User> findByEmailAndNameAndNickname(String email, String name, String nickname);

}
