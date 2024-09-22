package com.bank.bank.Controllers;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Services.ReservationService;
import com.bank.bank.Services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/customer")
    public String createCustomer(@Valid @RequestBody Customer customer) {
        return userService.createCustomer(customer);
    }

    @PostMapping("/employee")
    public String createEmployee(@Valid @RequestBody Employee employee) {
        return userService.createEmployee(employee);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployees() {
        return userService.getAllEmployees();
    }

    @GetMapping("/customer")
    public List<Customer> getCustmers() {
        return userService.getAllCustomers();
    }

    @DeleteMapping("/employee/{afm}")
    public String deleteEmployee(@PathVariable String afm) {
        return userService.deleteEmployee(afm);
    }

    @DeleteMapping("/customer/{afm}")
    public String deleteCustomer(@PathVariable String afm) {
        return userService.deleteCustomer(afm);
    }


    @PostMapping("/customer/{afm}/getLoan/{amount}")
    public String getLoan(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.getLoan(afm,amount);
    }

    @PostMapping("/customer/{afm}/payLoan/{amount}")
    public String payLoan(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.payLoan(afm,amount);
    }

    @PostMapping("/customer/{afm}/deposit/{amount}")
    public String deposit(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.deposit(afm,amount);
    }

    @PostMapping("/customer/{afm}/withdraw/{amount}")
    public String withdraw(@PathVariable String afm,@PathVariable BigDecimal amount){
        return userService.withdraw(afm,amount);
    }

    @GetMapping("/customer/{afm}/savings")
    public String getSavings(@PathVariable String afm){
        return userService.getSavings(afm);
    }

    @PostMapping("/customer/reservation/{customerAfm}")
    public Reservation createReservation(@RequestParam String employeeAfm,@PathVariable String customerAfm, @RequestParam Long slotId){
        return reservationService.createReservation(employeeAfm,customerAfm,slotId);
    }

    @DeleteMapping("/customer/reservation/{customerAfm}")
    public String deleteReservation(@PathVariable String customerAfm){
        return reservationService.deleteReservation(customerAfm);
    }

    @GetMapping("/customer/{customerAfm}/debt")
    public String getDebt(@PathVariable String afm){
        return userService.getDebt(afm);
    }

    @GetMapping("/employee/{afm}")
    public ResponseEntity<byte[]> printPdfByAfm(@PathVariable String afm){
       return userService.printPdfByAfm(afm);
    }

    @GetMapping("/reservation/{employeeAfm}")
    public List<Reservation> getReservations(@PathVariable String employeeAfm){
        return reservationService.getReservations(employeeAfm);
    }

}