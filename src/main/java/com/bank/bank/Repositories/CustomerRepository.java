package com.bank.bank.Repositories;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAfm(String afm);
    void deleteByAfm(String afm);
}