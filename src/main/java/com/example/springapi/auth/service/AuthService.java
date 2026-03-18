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
        log.info("Registration attempt for email={}, username={}", dto.email(), dto.username());
        try
        {
            if(userService.existsByUsername(dto.username()))
            {
                log.info("Registration failed, username={} already exists", dto.username());
                throw new AuthException("Username already exists");
            }
            if(userService.existsByEmail(dto.email()))
            {
                log.info("Registration failed, email={} already exists", dto.email());
                throw new AuthException("Email already exists");
            }
            String passHash = passEncoder.encode(dto.password());
            User user = new User(dto.email(),passHash, dto.username());
            User createdUser = userService.createUser(user);

            log.info("User registered successfully: id={}, email={}", createdUser.getId(),createdUser.getEmail());
            return createdUser;

        }
        catch(Exception e)
        {
            log.error("Registration failed for email={}, username={}",dto.email(),dto.username());
            throw e;

        }



    }
    public String login(LoginUserRequest dto)
    {
        User user = userService.getByEmail(dto.email());
        if(!passEncoder.matches(dto.password(),user.getPassHash())) {
            log.warn("Password mismatch for userId={}", user.getId());
            throw new AuthException("Invalid credentials");

        }
        log.info("Password verified for userId={}", user.getId());
        return "Login success";

    }
}
