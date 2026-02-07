package com.example.springapi.controllers;

import com.example.springapi.dto.DeleteResponse;
import com.example.springapi.dto.RegisterUserRequest;
import com.example.springapi.dto.UserRequest;
import com.example.springapi.dto.UserResponse;
import com.example.springapi.models.User;
import com.example.springapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest req)
    {
        User user = service.register(req);
        return new UserResponse(user.GetId(),user.GetUsername());
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id)
    {
        User user = service.getById(id);
        return new UserResponse(user.GetId(),user.GetUsername());
    }

    @GetMapping
    public List<UserResponse> getAll()
    {
        return service.getAll().stream().map(u -> new UserResponse(u.GetId(), u.GetUsername())).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long id)
    {
        boolean deleted = service.delete(id);
        if(deleted)
        {
            return ResponseEntity.ok(new DeleteResponse("User deleted successfully"));
        }
        else
        {
            return ResponseEntity.status(404).body(new DeleteResponse("User not found"));
        }
    }



}
