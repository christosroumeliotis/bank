package com.bank.bank.Controllers;

import com.bank.bank.Entities.Slot;
import com.bank.bank.Services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;

    // Create a new slot for an employee
    @PostMapping("/employee/{employeeAFM}")
    public Slot createSlot(@PathVariable String employeeAFM, @RequestParam String slotTime) {
        LocalDateTime slotDateTime = LocalDateTime.parse(slotTime).truncatedTo(ChronoUnit.MINUTES);; // Parse string to LocalDateTime
        return slotService.createSlotForEmployee(employeeAFM, slotDateTime);
    }

    // Get all slots for a specific employee
    @GetMapping("/employee/{employeeAFM}")
    public List<Slot> getSlotsForEmployee(@PathVariable String employeeAFM) {
        return slotService.getSlotsForEmployee(employeeAFM);
    }
}