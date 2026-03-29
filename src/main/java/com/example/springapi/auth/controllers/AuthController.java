package com.example.springapi.auth.controllers;

import com.example.springapi.auth.dto.LoginUserRequest;
import com.example.springapi.auth.dto.LoginUserResponse;
import com.example.springapi.auth.dto.RegisterUserRequest;
import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.dto.UserResponse;
import com.example.springapi.user.models.User;
import com.example.springapi.auth.service.AuthService;
import com.example.springapi.auth.exception.AuthException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController
{
    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserRequest dto)
    {
        log.info("POST /auth/register called for username={}", dto.username());
        UserDto user  =  authService.register(dto);
        log.info("Register succeeded for username={}", dto.username());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public LoginUserResponse login(@Valid @RequestBody LoginUserRequest dto)
    {
        log.info("POST /auth/login called for email = {}", dto.email());
        String token = authService.login(dto);
        log.info("Login succeeded for email = {}", dto.email());
        return new LoginUserResponse(token);
    }


}
