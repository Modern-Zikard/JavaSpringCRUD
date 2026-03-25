package com.example.springapi.user.dto;

import com.example.springapi.user.models.Role;

public record UserPatchDto(
        String username,
        String email,
        Role role
) {}
