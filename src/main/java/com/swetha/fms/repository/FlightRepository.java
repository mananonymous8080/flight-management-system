package com.swetha.fms.repository;

import com.swetha.fms.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "SELECT * FROM flights WHERE flight_name LIKE %:term%", nativeQuery = true)
    List<Flight> findByFlightNameContaining(@Param("term") String term);
}
