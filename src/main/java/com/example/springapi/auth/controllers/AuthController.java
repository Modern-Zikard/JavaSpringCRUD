package com.example.springapi.auth.controllers;

import com.example.springapi.auth.dto.LoginUserRequest;
import com.example.springapi.auth.dto.RegisterUserRequest;
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
    private final AuthService service;
    public AuthController(AuthService service)
    {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest dto)
    {
        log.info("POST /auth/register called");
        try
        {

            User user = service.register(dto);
            log.info("POST /auth/register succeeded for username={}", user.getUsername());
            return new UserResponse(user.getId(),user.getUsername());
        }
        catch(Exception e)
        {
            log.error("POST /auth/register failed: {}", e.getMessage());
            throw e;

        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserRequest dto)
    {
        log.info("POST /auth/login called");
        try
        {
            String result = service.login(dto);
            return ResponseEntity.ok(result);

        }
        catch(AuthException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());

        }

    }


}
