package com.bank.bank.Repositories;

import com.bank.bank.Entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    boolean existsByEmployeeAfmAndSlotTime(String employeeAfm, LocalDateTime slotTime);
    void deleteBySlotTimeBefore(LocalDateTime slotTime);

}
