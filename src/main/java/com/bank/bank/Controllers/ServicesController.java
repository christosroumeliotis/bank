package com.bank.bank.Controllers;

import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Services.ServicesService;
import com.bank.bank.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

   /* @Autowired
    private ServicesService servicesService;

    @PostMapping
    public UserEntity addUser(@Valid @RequestBody UserEntity userEntity) {
        return servicesService.saveUser(userEntity);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return servicesService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        servicesService.deleteUser(id);
    }*/
}
