package com.bank.bank.Entities;

import com.bank.bank.Enums.UserType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Date;


@Entity
public class UserEntity {

    @Id
    @NotBlank(message = "AFM must not be blank")
    @Column(name = "afm", unique = true, nullable = false)
    private String afm;
    @NotBlank
    @NotNull(message = "Firstname must not be null")
    private String firstName;
    @NotBlank
    @NotNull(message = "Lastname must not be null")
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull(message = "User type must not be null")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @NotNull(message = "Has_account must not be null")
    private boolean has_account;


    public UserEntity() {
    }

    public UserEntity(String afm, String firstName, String lastName, LocalDate birthDate, UserType userType, Boolean has_account) {
        this.afm=afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.userType = userType;
        this.has_account = has_account;
    }

    // Getters and setters


    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    @NotNull(message = "Has_account must not be null")
    public boolean isHas_account() {
        return has_account;
    }

    public void setHas_account(@NotNull(message = "Has_account must not be null") boolean has_account) {
        this.has_account = has_account;
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

    public @NotNull(message = "User type must not be null") UserType getUserType() {
        return userType;
    }

    public void setUserType( @NotNull(message = "User type must not be null") UserType userType) {
        this.userType = userType;
    }

    public @NotNull(message = "Has_account must not be null") Boolean getHas_account() {
        return has_account;
    }

    public void setHas_account( @NotNull(message = "Has_account must not be null") Boolean has_account) {
        this.has_account = has_account;
    }
}