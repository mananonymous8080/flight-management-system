package com.swetha.fms.repository;

import com.swetha.fms.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findByUsername(String username);
}
