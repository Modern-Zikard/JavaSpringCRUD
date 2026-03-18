package com.example.springapi.controllers;

import com.example.springapi.dto.RegisterUserRequest;
import com.example.springapi.dto.UserResponse;
import com.example.springapi.models.User;
import com.example.springapi.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController
{
    private final UserService service;
    public AuthController(UserService service)
    {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest req)
    {
        log.info("POST /auth/register called");
        try
        {

            User user = service.register(req);
            log.info("POST /auth/register succeeded for username={}", user.GetUsername());
            return new UserResponse(user.GetId(),user.GetUsername());
        }
        catch(Exception e)
        {
            log.error("POST /auth/register failed: {}", e.getMessage());
            throw e;

        }

    }


}
