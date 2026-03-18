package com.example.springapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserRequest(
    @NotBlank
    @Email(message = "incorrect email") String email,
    @NotBlank(message = "Enter password") @Size(min = 6, message = "A password of at least 6 characters is required") String password
            ){}

