package com.bank.bank.Repositories;

import com.bank.bank.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByAfm(String afm);
    void deleteByAfm(String afm);
}
