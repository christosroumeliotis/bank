package com.bank.bank.Repositories;


import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    boolean existsByCustomerAfm(String customerAfm);
    boolean existsBySlotId(Long Id);

    Reservation findByCustomerAfm(String customerAfm);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.slot.slotTime < :currentDateTime")
    void deleteReservationsForExpiredSlots(LocalDateTime currentDateTime);

    @Query("SELECT r FROM Reservation r WHERE r.slot.slotTime < :currentDateTime")
    List<Reservation> selectExpiredReservation(LocalDateTime currentDateTime);
}
