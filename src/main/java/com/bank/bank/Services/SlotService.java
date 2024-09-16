package com.bank.bank.Services;

import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Slot;
import com.bank.bank.Repositories.EmployeeRepository;
import com.bank.bank.Repositories.SlotRepository;
import com.bank.bank.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotsRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SlotRepository slotRepository;

    // Create a new slot for an employee
    public Slot createSlotForEmployee(String employeeId, LocalDateTime slotTime) {
        Employee employee = employeeRepository.findByAfm(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNextDay = now.plusDays(1).toLocalDate().atStartOfDay();

        if (slotTime.isBefore(startOfNextDay)) {
            throw new ResourceNotFoundException("Slot time must be at least one day after.");
        }

        boolean slotExists = slotRepository.existsByEmployeeAfmAndSlotTime(employeeId, slotTime);

        if (slotExists) {
            throw new ResourceNotFoundException("Slot for this employee at this time already exists.");
        }
        Slot slot = new Slot(slotTime, employee);
        return slotsRepository.save(slot);
    }

    // Get all slots for a specific employee
    public List<Slot> getSlotsForEmployee(String employeeId) {
        return employeeRepository.findByAfm(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"))
                .getSlots();
    }
    @Scheduled(cron = "0 0 * * * *")
    public void deleteExpiredSlots() {
        LocalDateTime now = LocalDateTime.now();
        slotRepository.deleteBySlotTimeBefore(now);
    }

}
