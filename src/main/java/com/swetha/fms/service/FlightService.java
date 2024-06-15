package com.swetha.fms.service;

import com.swetha.fms.enums.AuthorityType;
import com.swetha.fms.model.Flight;
import com.swetha.fms.repository.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void save(Flight flight) {
        flightRepository.save(flight);
    }

    public Optional<Flight> findFlightById(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight;
    }
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }
    public Page<Flight> findPaginated(Pageable pageable, String term, AuthorityType authorityType) {

        return page(pageable, term, authorityType);
    }
    private Page<Flight> page(Pageable pageable, String term, AuthorityType authorityType) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        ArrayList<Flight> flights;
        List<Flight> list;

        if (term == null) {
            flights = (ArrayList<Flight>) flightRepository.findAll();
        } else {
            flights = (ArrayList<Flight>) flightRepository.findByFlightNameContaining(term);
        }

        if(AuthorityType.USER.equals(authorityType)) {
            flights.removeIf(flight -> ! flight.isEnabled());
        }

        if (flights.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, flights.size());
            list = flights.subList(startItem, toIndex);
        }

        Page<Flight> flightPage = new PageImpl<Flight>(list, PageRequest.of(currentPage, pageSize), flights.size());
        return flightPage;
    }

}
