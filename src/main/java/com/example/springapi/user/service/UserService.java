package com.example.springapi.user.service;


import com.example.springapi.auth.dto.RegisterUserRequest;
import com.example.springapi.auth.exception.UserNotFoundException;
import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.dto.UserPatchDto;
import com.example.springapi.user.models.Role;
import com.example.springapi.user.models.User;
import com.example.springapi.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public boolean existsByUsername(String username){ return repo.existsByUsername(username);}
    public boolean existsByEmail(String email){ return repo.existsByEmail(email);}
    public boolean existsById(Long id){ return repo.existsById(id);}

    public User getUserByEmail(String email)
    {
        return repo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User getUserById(Long id)
    {
        return repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User getUserByUsername(String username)
    {
        return repo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
    }

    public UserDto updateUser(Long id, UserDto updatedData)
    {
        User existingUser = repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));

        existingUser.setUsername(updatedData.username());
        existingUser.setRole(Role.fromString(updatedData.role()));
        existingUser.setEmail(updatedData.email());
        repo.save(existingUser);

        return new UserDto(
                existingUser.getId(),
                existingUser.getUsername(),
                existingUser.getEmail(),
                existingUser.getRole().toString()
        );
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

    public UserDto getUserDtoByEmail(String email)
    {
        User user = repo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public UserDto getUserDtoById(Long id)
    {
        User user = repo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public UserDto getUserDtoByUsername(String username)
    {
        User user = repo.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public List<UserDto> getAll()
    {
        return repo.findAll().stream().map(u -> new UserDto(u.getId(), u.getUsername(), u.getEmail(), u.getRole().toString())).toList();
    }

    public void delete(Long id) {
        if(!repo.existsById(id))
        {
            throw new UserNotFoundException("User not found with id = " + id);
        }
        repo.deleteById(id);
    }
}
