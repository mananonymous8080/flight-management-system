package com.swetha.fms.controller;

import com.swetha.fms.enums.AuthorityType;
import com.swetha.fms.model.Flight;
import com.swetha.fms.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final FlightService flightService;

    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = { "", "/" })
    public String adminPage( Model model, @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        return page(null, model, page, size);
    }

    @GetMapping("/addFlight")
    public String addBook(Model model) {
        model.addAttribute("flight", new Flight());
        return "addFlightForm";
    }

    @PostMapping("/saveFlight")
    public String saveBook(@Valid Flight flight, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "addFlightForm";
        }
        flightService.save(flight);
        redirect.addFlashAttribute("successMessage", "Added flight successfully!");
        return "redirect:/admin";
    }

    @GetMapping("/updateFlight/{id}")
    public String updateFlight(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", flightService.findFlightById(id));
        return "addFlightForm";
    }

    @GetMapping("/deleteFlight/{id}")
    public String deleteFlight(@PathVariable Long id, RedirectAttributes redirect) {
        flightService.delete(id);
        redirect.addFlashAttribute("successMessage", "Deleted flight successfully!");
        return "redirect:/admin";
    }


    @GetMapping("/searchFlight")
    public String searchBooks(@RequestParam("term") String term, Model model,
                              @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        if (term.isBlank()) {
            return "redirect:/";
        }
        return page(term, model, page, size);
    }

    private String page(@RequestParam("term") String term, Model model, @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Flight> flightPage;

        if (term == null) {
            flightPage = flightService.findPaginated(PageRequest.of(currentPage - 1, pageSize), null, AuthorityType.ADMIN);
        } else {
            flightPage = flightService.findPaginated(PageRequest.of(currentPage - 1, pageSize), term, AuthorityType.ADMIN);
        }
        model.addAttribute("flightPage", flightPage);

        int totalPages = flightPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "flightListAdmin";
    }
}
