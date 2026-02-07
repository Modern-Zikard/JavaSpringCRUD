package com.example.springapi.service;


import com.example.springapi.dto.RegisterUserRequest;
import com.example.springapi.models.User;
import com.example.springapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService
{
    private final UserRepository repo;
    private final PasswordEncoder passEncoder;

    public UserService(UserRepository repo, PasswordEncoder passEncoder)
    {
        this.repo = repo;
        this.passEncoder = passEncoder;
    }
    public User register(RegisterUserRequest dto)
    {
        String PassHash = passEncoder.encode(dto.password());
        return repo.save(new User(dto.email(), PassHash, dto.username()));
    }

    public User getById(Long id)
    {
        return repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public List<User> getAll()
    {
        return repo.findAll();
    }

    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
