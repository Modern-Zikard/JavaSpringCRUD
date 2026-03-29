package com.example.springapi.user.controllers;

import com.example.springapi.auth.principal.UserPrincipal;
import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users/me")
@Slf4j
public class UserController
{
    private final UserService userService;

    public UserController(UserService service)
    {
        this.userService = service;
    }

    @GetMapping
    public UserDto getMe(@AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        return userService.getUserDtoById(userPrincipal.id());
    }













}
