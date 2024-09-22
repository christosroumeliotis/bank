package com.bank.bank.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Customer extends UserEntity {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email must not be null")
    private String email;
    @Column(columnDefinition = "DECIMAL(19, 2) DEFAULT 0.00")
    private BigDecimal loanDebt=BigDecimal.ZERO;
    @Column(columnDefinition = "DECIMAL(19, 2) DEFAULT 0.00")
    private BigDecimal moneyDeposited=BigDecimal.ZERO;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Loan loan;

    // Constructors, getters, and setters
    public Customer() {}

    public Customer(String afm, String firstName, String lastName, LocalDate birthDate,String email) {
        super(afm,firstName,lastName,birthDate);
        this.email = email;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMoneyDeposited() {
        return moneyDeposited;
    }

    public void setMoneyDeposited(BigDecimal moneyDeposited) {
        this.moneyDeposited = moneyDeposited;
    }

    public BigDecimal getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(BigDecimal loanDebt) {
        this.loanDebt = loanDebt;
    }
}
