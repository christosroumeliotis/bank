package com.bank.bank.Entities;

import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_afm", nullable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "customer_afm", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    public Reservation(){}

    public Reservation(Employee employee, Customer customer, Slot slot) {
        this.employee = employee;
        this.customer = customer;
        this.slot = slot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
