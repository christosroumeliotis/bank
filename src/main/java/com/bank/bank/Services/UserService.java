package com.bank.bank.Services;

import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Repositories.UserRepository;
import com.bank.bank.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String saveUser(UserEntity userEntity) {
        if (userRepository.findByAfm(userEntity.getAfm()).isPresent()) {
            // Return message if the user with the same AFM already exists
            return "User with AFM " + userEntity.getAfm() + " already exists.";
        } else {
            // Save the new user and return a success message
            userRepository.save(userEntity);
            return "User with AFM " + userEntity.getAfm() + " has been successfully added.";
        }

    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public String deleteUser(String afm) {
        if (userRepository.findByAfm(afm).isPresent()) {
            userRepository.deleteByAfm(afm);
            return "User with AFM " + afm + " has been successfully deleted.";
        } else {
            return"User not found with AFM: " + afm;
        }
    }
}