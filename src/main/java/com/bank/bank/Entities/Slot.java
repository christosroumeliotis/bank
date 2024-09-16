package com.bank.bank.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import jakarta.persistence.Id;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date-Time must not be null")
    private LocalDateTime slotTime;

    @ManyToOne
    @JoinColumn(name = "employee_afm", nullable = false) // This will create a foreign key column
    @JsonIgnore
    private Employee employee;

    // Constructors, getters, and setters
    public Slot() {}

    public Slot(LocalDateTime slotTime, Employee employee) {
        this.slotTime = slotTime.truncatedTo(ChronoUnit.MINUTES);;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(LocalDateTime slotTime) {
        this.slotTime = slotTime.truncatedTo(ChronoUnit.MINUTES);;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
