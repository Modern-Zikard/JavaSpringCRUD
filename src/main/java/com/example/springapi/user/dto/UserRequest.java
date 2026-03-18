package com.example.springapi.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
       @NotBlank(message = "please enter name") String username
) {}
