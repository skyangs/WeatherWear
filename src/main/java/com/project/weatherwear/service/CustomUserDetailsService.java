package com.project.weatherwear.service;

import com.project.weatherwear.repository.UserRepository;
import com.project.weatherwear.domain.dto.UserDTO;
import com.project.weatherwear.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.get().getRole()));
        ModelMapper mapper = new ModelMapper();
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userDTO.setAuthorities(authorities);

        return userDTO;
    }

}
