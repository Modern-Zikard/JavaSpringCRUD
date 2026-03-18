package com.example.springapi.user.service;


import com.example.springapi.user.models.User;
import com.example.springapi.user.repository.UserRepository;
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
    public User createUser(User user)
    {
        return repo.save(user);
    }
    public User getByEmail(String email)
    {
        return repo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
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
