package com.bank.bank.Controllers;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Services.ReservationService;
import com.bank.bank.Services.SlotService;
import com.bank.bank.Services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/public")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SlotService slotService;

    // Get all slots for a specific employee
    @GetMapping("/{employeeAFM}")
    public List<Slot> getSlotsForEmployee(@PathVariable String employeeAFM) {
        return slotService.getSlotsForEmployee(employeeAFM);
    }

    @GetMapping("/{afm}/savings")
    public String getSavings(@PathVariable String afm){
        return userService.getSavings(afm);
    }

    @GetMapping("/{customerAfm}/debt")
    public String getDebt(@PathVariable String customerAfm){
        return userService.getDebt(customerAfm);
    }

}