package com.hasikowski.demo.controller;

import com.hasikowski.demo.config.UserAuthProvider;
import com.hasikowski.demo.Dto.CredentialsDto;
import com.hasikowski.demo.Dto.SignUpDto;
import com.hasikowski.demo.Dto.UserDto;
import com.hasikowski.demo.model.UserEntity;
import com.hasikowski.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public AuthController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
       UserDto user = userService.login(credentialsDto);
       user.setToken(userAuthProvider.createToken(user));
       return  ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto){
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> findUserByFirstName(@RequestParam(value = "firstName") String firstName){
        return new ResponseEntity<>(this.userService.findUserByFirstName(firstName), HttpStatus.OK);
    }

}
