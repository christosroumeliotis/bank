package com.bank.bank.Controllers;

import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public String addUser(@Valid @RequestBody UserEntity userEntity) {
        return userService.saveUser(userEntity);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{afm}")
    public String deleteUser(@PathVariable String afm) {
        return userService.deleteUser(afm);
    }
}