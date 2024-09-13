package com.bank.bank.Controllers;

import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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


    @PostMapping("/{afm}/getLoan/{amount}")
    public String getLoan(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.getLoan(afm,amount);
    }

    @PostMapping("/{afm}/payLoan/{amount}")
    public String payLoan(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.payLoan(afm,amount);
    }

    @PostMapping("/{afm}/deposit/{amount}")
    public String deposit(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.deposit(afm,amount);
    }

    @PostMapping("/{afm}/withdraw/{amount}")
    public String withdraw(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.withdraw(afm,amount);
    }

    @GetMapping("/{afm}/savings")
    public String getSavings(@PathVariable String afm){
        return userService.getSavings(afm);
    }
    @GetMapping("/{afm}/debt")
    public String getDebt(@PathVariable String afm){
        return userService.getDebt(afm);
    }

    @GetMapping("/EMPLOYEE/{afm}")
    public ResponseEntity<byte[]> printPdfByAfm(@PathVariable String afm){
       return userService.printPdfByAfm(afm);
    }

}