package com.bank.bank.Repositories;


import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    boolean existsByCustomerAfm(String customerAfm);
    boolean existsBySlotId(Long Id);

    Reservation findByCustomerAfm(String customerAfm);
}
