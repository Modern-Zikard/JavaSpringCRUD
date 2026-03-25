package com.example.springapi.user.controllers;

import com.example.springapi.auth.principal.UserPrincipal;
import com.example.springapi.user.dto.DeleteResponse;
import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.dto.UserPatchDto;
import com.example.springapi.user.dto.UserResponse;
import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users/me")
@Slf4j
public class UserController
{
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @GetMapping
    public UserDto getMe(@AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        User user = service.getById(userPrincipal.id());
        return new UserDto(user.getId(),user.getUsername(),user.getEmail(), user.getRole().toString());

    }













}
