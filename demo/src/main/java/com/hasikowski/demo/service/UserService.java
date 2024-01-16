package com.hasikowski.demo.service;

import com.hasikowski.demo.exceptions.AppException;
import com.hasikowski.demo.mapper.UserMapper;
import com.hasikowski.demo.Dto.CredentialsDto;
import com.hasikowski.demo.Dto.SignUpDto;
import com.hasikowski.demo.model.UserEntity;
import com.hasikowski.demo.Dto.UserDto;
import com.hasikowski.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto login(CredentialsDto credentialsDto){
        UserEntity user = userRepository.findUserByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())){
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);

    }

    public UserDto register(SignUpDto signUpDto){
        Optional<UserEntity> oUser = userRepository.findUserByLogin(signUpDto.login());

        if (oUser.isPresent()){
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        user.setRole("user");
        UserEntity savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserEntity findUserByFirstName(String firstName){
        UserEntity user = userRepository.findUserByFirstName(firstName).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return user;
    }

}
