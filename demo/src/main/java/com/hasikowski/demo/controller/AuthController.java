package com.hasikowski.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hasikowski.demo.config.UserAuthProvider;
import com.hasikowski.demo.Dto.CredentialsDto;
import com.hasikowski.demo.Dto.SignUpDto;
import com.hasikowski.demo.Dto.UserDto;
import com.hasikowski.demo.model.User;
import com.hasikowski.demo.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        user.setRole("user");
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @GetMapping("/user")
    public ResponseEntity<User> findUserByFirstName(@RequestParam(value = "firstName") String firstName){
        return new ResponseEntity<>(this.userService.findUserByFirstName(firstName), HttpStatus.OK);
    }

}
