package com.example.springapi.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
       @NotBlank(message = "please enter name") String username
) {}
