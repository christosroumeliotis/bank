package com.bank.bank.Repositories;

import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByAfm(String afm);
    void deleteByAfm(String afm);
}