package com.example.springapi.admin.controllers;

import com.example.springapi.user.dto.DeleteResponse;
import com.example.springapi.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @DeleteMapping("/{id}")
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

}
