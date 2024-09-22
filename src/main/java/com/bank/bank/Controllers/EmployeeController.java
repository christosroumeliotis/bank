package com.bank.bank.Controllers;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Reservation;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Services.ReservationService;
import com.bank.bank.Services.SlotService;
import com.bank.bank.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/employee/users")
public class EmployeeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SlotService slotService;


    @PostMapping("/add/employee")
    public String createEmployee(@Valid @RequestBody Employee employee) {
        return userService.createEmployee(employee);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployees() {
        return userService.getAllEmployees();
    }

    @DeleteMapping("/employee/{afm}")
    public String deleteEmployee(@PathVariable String afm) {
        return userService.deleteEmployee(afm);
    }

    @PostMapping("/employee/{employeeAFM}")
    public Slot createSlot(@PathVariable String employeeAFM, @RequestParam String slotTime) {
        LocalDateTime slotDateTime = LocalDateTime.parse(slotTime).truncatedTo(ChronoUnit.MINUTES);; // Parse string to LocalDateTime
        return slotService.createSlotForEmployee(employeeAFM, slotDateTime);
    }

    @GetMapping("/employee/reservation/{employeeAfm}")
    public List<Reservation> getReservations(@PathVariable String employeeAfm){
        return reservationService.getReservations(employeeAfm);
    }

    @GetMapping("/employee/{afm}")
    public ResponseEntity<byte[]> printPdfByAfm(@PathVariable String afm){
        return userService.printPdfByAfm(afm);
    }

}
