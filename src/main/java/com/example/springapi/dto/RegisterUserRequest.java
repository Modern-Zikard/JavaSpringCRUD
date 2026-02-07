package com.example.springapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest (
        @NotBlank @Email(message = "incorrect email") String email,
        @NotBlank(message = "Enter password") @Size(min = 6, message = "A password of at least 6 characters is required") String password,
        @NotBlank(message = "Enter username") @Size(min = 2, max = 50, message = "Invalid username") String username){}
