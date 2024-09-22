package com.bank.bank.Controllers;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Services.ReservationService;
import com.bank.bank.Services.SlotService;
import com.bank.bank.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("customer/users")
public class CustomerController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/add/customer")
    public String createCustomer(@Valid @RequestBody Customer customer) {
        return userService.createCustomer(customer);
    }

    @GetMapping("/customer")
    public List<Customer> getCustomers() {
        return userService.getAllCustomers();
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

    @PostMapping("/customer/reservation/{customerAfm}")
    public Reservation createReservation(@RequestParam String employeeAfm, @PathVariable String customerAfm, @RequestParam Long slotId){
        return reservationService.createReservation(employeeAfm,customerAfm,slotId);
    }

    @DeleteMapping("/customer/reservation/{customerAfm}")
    public String deleteReservation(@PathVariable String customerAfm){
        return reservationService.deleteReservation(customerAfm);
    }

}
