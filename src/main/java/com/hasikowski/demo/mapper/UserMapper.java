package com.hasikowski.demo.mapper;

import com.hasikowski.demo.Dto.SignUpDto;
import com.hasikowski.demo.model.UserEntity;
import com.hasikowski.demo.Dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDto toUserDto(UserEntity user);

     @Mapping(target = "password", ignore = true)
     UserEntity signUpToUser(SignUpDto signUpDto);

}
