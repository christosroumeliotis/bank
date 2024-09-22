package com.bank.bank.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.Lock;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private Long id;
    private BigDecimal amount;
    private LocalDateTime signingDate;

    @OneToOne
    @JoinColumn(name = "customer_afm", referencedColumnName = "afm")
    private Customer customer;


    public Loan() {
    }

    public Loan(BigDecimal amount, Customer customer) {
        this.amount = amount;
        this.customer = customer;
        this.signingDate= LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(LocalDateTime signingDate) {
        this.signingDate = signingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
