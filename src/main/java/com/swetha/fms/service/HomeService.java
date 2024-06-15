package com.swetha.fms.service;

import com.swetha.fms.enums.AuthorityType;
import com.swetha.fms.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class HomeService {
    private final FlightService flightService;
    private static final int pageSizeDefault = 6;
    public HomeService(FlightService flightService) {
        this.flightService = flightService;
    }


    public String page(@RequestParam("term") String term, Model model, @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(pageSizeDefault);

        Page<Flight> flightPage;

        if (term == null) {
            flightPage = flightService.findPaginated(PageRequest.of(currentPage - 1, pageSize), null, AuthorityType.USER);
        } else {
            flightPage = flightService.findPaginated(PageRequest.of(currentPage - 1, pageSize), term, AuthorityType.USER);
        }
        model.addAttribute("flightPage", flightPage);

        int totalPages = flightPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }
}
