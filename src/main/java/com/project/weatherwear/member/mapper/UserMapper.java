package com.project.weatherwear.member.mapper;

import com.project.weatherwear.member.domain.User;
import com.project.weatherwear.member.dto.RequestRegisterUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

     User toUser(RequestRegisterUserDTO userDTO);
}
