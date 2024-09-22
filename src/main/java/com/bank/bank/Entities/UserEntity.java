package com.bank.bank.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserEntity {

    @Id
    @NotBlank(message = "AFM must not be blank")
    @Pattern(regexp = "\\d+", message = "AFM must contain only numbers")
    @Column(name = "afm", unique = true, nullable = false)
    private String afm;
    @NotBlank(message = "Username must not be blank")
    @NotNull(message = "Username must not be null")
    private String username;
    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    private String password;
    @NotBlank(message = "Firstname must not be blank")
    @NotNull(message = "Firstname must not be null")
    private String firstName;
    @NotBlank(message = "Lastname must not be null")
    @NotNull(message = "Lastname must not be null")
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String role;


    public UserEntity() {
    }

    public UserEntity(String afm, String firstName, String lastName, LocalDate birthDate,String username,String password) {
        this.afm=afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.username=username;
        this.password= password;
    }

    // Getters and setters


    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public @NotBlank @NotNull(message = "Lastname must not be null") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @NotNull(message = "Lastname must not be null") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @NotNull(message = "Firstname must not be null") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @NotNull(message = "Firstname must not be null") String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate( LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public @NotBlank(message = "Username must not be blank") @NotNull(message = "Username must not be null") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username must not be blank") @NotNull(message = "Username must not be null") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Lastname must not be blank") @NotNull(message = "Lastname must not be null") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Lastname must not be blank") @NotNull(message = "Lastname must not be null") String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}