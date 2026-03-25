package com.example.springapi.user.service;


import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.dto.UserPatchDto;
import com.example.springapi.user.models.Role;
import com.example.springapi.user.models.User;
import com.example.springapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService
{
    @Autowired
    private final UserRepository repo;

    public UserService(UserRepository repo)
    {
        this.repo = repo;
    }
    public User createUser(User user)
    {
        user.setRole(Role.USER);
        return repo.save(user);
    }

    public User updateUser(Long id, User updatedData)
    {
        User existingUser = repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));

        existingUser.setUsername(updatedData.getUsername());
        existingUser.setRole(updatedData.getRole());
        existingUser.setEmail(updatedData.getEmail());

        return repo.save(existingUser);
    }

    public UserDto patchUser(Long id, UserPatchDto dto)
    {
        User user = repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        if(dto.username()!=null)
            user.setUsername(dto.username());
        if(dto.email()!=null)
            user.setEmail(dto.email());
        if(dto.role()!=null)
            user.setRole(dto.role());

        User saved = repo.save(user);

        return new UserDto(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole().toString()
        );
    }

    public User getByEmail(String email) {return repo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));}
    public User getById(Long id)
    {
        return repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User getByUsername(String username) {return repo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));}

    public boolean existsByUsername(String username){ return repo.existsByUsername(username);}
    public boolean existsByEmail(String email){ return repo.existsByEmail(email);}
    public boolean existsById(Long id){ return repo.existsById(id);}

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
