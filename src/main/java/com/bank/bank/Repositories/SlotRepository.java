package com.bank.bank.Repositories;

import com.bank.bank.Entities.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    boolean existsByEmployeeAfmAndSlotTime(String employeeAfm, LocalDateTime slotTime);
    List<Slot> findBySlotTimeBefore(LocalDateTime currentDateTime);
    @Transactional
    @Modifying
    @Query("DELETE FROM Slot s WHERE s.slotTime < :currentDateTime")
    void deleteBySlotTimeBefore(LocalDateTime currentDateTime);

}
