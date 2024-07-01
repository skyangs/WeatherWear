package com.project.weatherwear.user.mapper;

import com.project.weatherwear.user.domain.User;
import com.project.weatherwear.user.dto.RequestRegisterUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

     User toUser(RequestRegisterUserDTO userDTO);
}
