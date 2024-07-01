package com.project.weatherwear.member.repository;

import com.project.weatherwear.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
