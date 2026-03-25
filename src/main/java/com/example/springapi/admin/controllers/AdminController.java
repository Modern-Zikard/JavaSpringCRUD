package com.example.springapi.admin.controllers;

import com.example.springapi.user.dto.DeleteResponse;
import com.example.springapi.user.dto.UserDto;
import com.example.springapi.user.dto.UserPatchDto;
import com.example.springapi.user.dto.UserResponse;
import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController
{
    private final UserService service;

    public AdminController(UserService service)
    {
        this.service = service;
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long id)
    {
        log.info("DELETE /users/{} called", id);
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
    @GetMapping("/users/{id}")
    public UserResponse getById(@PathVariable Long id)
    {
        log.info("GET /users/{} called", id);
        try
        {
            User user = service.getById(id);
            log.info("GET /users/{} succeeded", id);
            return new UserResponse(user.getId(), user.getUsername());
        }
        catch(Exception e)
        {
            log.error("GET /users/{} failed: {}", id, e.getMessage());
            throw e;

        }
    }

    @GetMapping("/users")
    public List<UserResponse> getAll()
    {
        log.info("GET /users called");
        try
        {   log.info("GET /users succeeded");
            return service.getAll().stream().map(u -> new UserResponse(u.getId(), u.getUsername())).toList();
        }
        catch(Exception e)
        {
            log.error("GET /users failed: {}", e.getMessage());
            throw e;

        }
    }

    @PatchMapping("/users/patch={id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody UserPatchDto dto)
    {
        return ResponseEntity.ok(service.patchUser(id, dto));
    }


}
