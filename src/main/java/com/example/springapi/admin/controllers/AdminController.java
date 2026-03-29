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
    private final UserService userService;

    public AdminController(UserService service)
    {
        this.userService = service;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long id)
    {
        log.info("DELETE /admin/users/{} called", id);
        userService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("User deleted successfully"));

    }
    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable Long id)
    {
        log.info("GET /admin/users/{} called", id);

        UserDto userDto = userService.getUserDtoById(id);

        log.info("GET /admin/users/{} secceeded", id);
        return userDto;
    }

    @GetMapping("/users")
    public List<UserDto> getAll()
    {
        log.info("GET /admin/users called");
        List<UserDto> users = userService.getAll();
        log.info("GET /admin/users succeeded, count={}", users.size());
        return users;

    }

    @PatchMapping("/users/patch={id}")
    public UserDto patchUser(@PathVariable Long id, @RequestBody UserPatchDto dto)
    {
        log.info("PATCH /admin/users/patch={} called", id);
        UserDto updated = userService.patchUser(id, dto);
        log.info("PATCH /admin/users/patch={} succeeded", id);
        return updated;
    }


}
