package com.bank.bank.Entities;

import com.bank.bank.Enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
public class UserEntity {

    @Id
    @NotBlank(message = "AFM must not be blank")
    @Pattern(regexp = "\\d+", message = "AFM must contain only numbers")
    @Column(name = "afm", unique = true, nullable = false)
    private String afm;
    @NotBlank(message = "Firstname must not be blank")
    @NotNull(message = "Firstname must not be null")
    private String firstName;
    @NotBlank(message = "Lastname must not be null")
    @NotNull(message = "Lastname must not be null")
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull(message = "User type must not be null")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(columnDefinition = "DECIMAL(19, 2) DEFAULT 0.00")
    private BigDecimal loanDebt=BigDecimal.ZERO;
    @Column(columnDefinition = "DECIMAL(19, 2) DEFAULT 0.00")
    private BigDecimal moneyDeposited=BigDecimal.ZERO;


    public UserEntity() {
    }

    public UserEntity(String afm, String firstName, String lastName, LocalDate birthDate, UserType userType) {
        this.afm=afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.userType = userType;

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

    public @NotNull(message = "User type must not be null") UserType getUserType() {
        return userType;
    }

    public void setUserType( @NotNull(message = "User type must not be null") UserType userType) {
        this.userType = userType;
    }


    public BigDecimal getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(BigDecimal loanDebt) {
        this.loanDebt = loanDebt;
    }

    public BigDecimal getMoneyDeposited() {
        return moneyDeposited;
    }

    public void setMoneyDeposited(BigDecimal moneyDeposited) {
        this.moneyDeposited = moneyDeposited;
    }
}