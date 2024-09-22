package com.bank.bank.Repositories;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAfm(String afm);
    void deleteByAfm(String afm);
    Customer findByUsername(String username);

    List<Customer> findByPassword(String password);
}