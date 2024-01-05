package com.hasikowski.demo.mapper;

import com.hasikowski.demo.Dto.SignUpDto;
import com.hasikowski.demo.model.User;
import com.hasikowski.demo.Dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDto toUserDto(User user);

     @Mapping(target = "password", ignore = true)
     User signUpToUser(SignUpDto signUpDto);

}
