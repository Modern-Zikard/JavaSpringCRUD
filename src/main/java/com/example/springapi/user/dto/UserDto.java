package com.example.springapi.user.dto;

public record UserDto(
        Long id,
        String username,
        String email,
        String role) {}
