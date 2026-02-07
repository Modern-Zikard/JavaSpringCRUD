package com.example.springapi.service;

import com.example.springapi.models.User;
import com.example.springapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService
{
    private final UserRepository repo;
    public UserService(UserRepository repo)
    {
        this.repo = repo;
    }
    public User Create(String username)
    {
        return repo.save(new User(username));
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
