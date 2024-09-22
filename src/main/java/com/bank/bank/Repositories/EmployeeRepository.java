package com.bank.bank.Repositories;

import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByAfm(String afm);
    void deleteByAfm(String afm);
    Employee findByUsername(String username);
    List<Employee> findByPassword(String password);
}