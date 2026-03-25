package com.example.springapi.auth.principal;

public record UserPrincipal(
        Long id,
        String email,
        String username
){}
