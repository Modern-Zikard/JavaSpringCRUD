package com.example.springapi.auth.service;


import com.example.springapi.auth.dto.LoginUserRequest;
import com.example.springapi.auth.dto.RegisterUserRequest;
import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.example.springapi.auth.exception.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class AuthService
{
    private final UserService userService;
    private final PasswordEncoder passEncoder;
    public AuthService(UserService userService, PasswordEncoder passEncoder)
    {
        this.userService = userService;
        this.passEncoder = passEncoder;
    }
    public User register(RegisterUserRequest dto)
    {
        String passHash = passEncoder.encode(dto.password());
        User user = new User(dto.email(),passHash, dto.username());
        return userService.createUser(user);
    }
    public String login(LoginUserRequest dto)
    {
        User user = userService.getByEmail(dto.email());
        if(!passEncoder.matches(dto.password(),user.getPassHash())) {
            throw new AuthException("Invalid credentials");

        }
        return "Login success";

    }
}
