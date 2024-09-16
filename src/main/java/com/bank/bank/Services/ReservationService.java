package com.bank.bank.Services;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Repositories.EmployeeRepository;
import com.bank.bank.Repositories.ReservationRepository;
import com.bank.bank.Repositories.SlotRepository;
import com.bank.bank.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SlotRepository slotRepository;

    public Reservation createReservation(String employeeAfm,String customerAfm,Long slotId){
        Employee employee = employeeRepository.findByAfm(employeeAfm)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Customer customer =customerRepository.findByAfm(customerAfm)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found"));

        boolean slotFound=reservationRepository.existsBySlotId(slotId);
        if(slotFound){
            throw  new ResourceNotFoundException("Slot is taken");
        }

        boolean customerHasReservation=reservationRepository.existsByCustomerAfm(customerAfm);
        if(customerHasReservation){
            throw  new ResourceNotFoundException("This Customer already has a reservation");
        }

        Reservation reservation = new Reservation(employee,customer,slot);
        return reservationRepository.save(reservation);
    }

    public String deleteReservation(String customerAfm){
        Customer customer =customerRepository.findByAfm(customerAfm)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        boolean customerHasReservation=reservationRepository.existsByCustomerAfm(customerAfm);
        if(!customerHasReservation){
            throw  new ResourceNotFoundException("This Customer doesn't have reservation");
        }

        Long id = reservationRepository.findByCustomerAfm(customerAfm).getId();
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        Employee employee = reservation.getEmployee();
        employee.getReservations().remove(reservation);

        reservationRepository.deleteById(id);
        return "Reservation for customer with afm "+customerAfm+" deleted";

    }

    public List<Reservation> getReservations(String employeeAfm) {
        Employee employee = employeeRepository.findByAfm(employeeAfm)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employee.getReservations();
    }
}
