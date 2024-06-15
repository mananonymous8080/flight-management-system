package com.swetha.fms.controller;

import com.swetha.fms.model.Booking;
import com.swetha.fms.model.Flight;
import com.swetha.fms.repository.BookingRepository;
import com.swetha.fms.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/book/{flightId}")
    public String bookFlightForm(@PathVariable Long flightId, Model model) {
        Optional<Flight> flight = flightService.findFlightById(flightId);
        model.addAttribute("flight", flight.get());
        Booking booking = new Booking();
        model.addAttribute("booking", booking);
        return "bookFlightForm";
    }

    @PostMapping("/book")
    public String confirmBooking(@ModelAttribute("booking") Booking booking, @RequestParam("username") String username) {
        // Set the username for the booking
        booking.setUsername(username);
        // Save the booking details to the booking table
        bookingRepository.save(booking);

        // Redirect to show booking page
        return "redirect:/booking/show?username="+username;
    }
    @GetMapping("/show")
    public String showBooking(@RequestParam("username") String username, Model model) {
        List<Booking> bookings = bookingRepository.findByUsername(username);
        model.addAttribute("bookings", bookings);
        return "showBookings";
    }


}
