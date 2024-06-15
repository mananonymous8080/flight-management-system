package com.swetha.fms.controller;

import com.swetha.fms.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(value = { "", "/" })
    public String homePage(Model model, HttpSession httpSession, @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        String username = (String) httpSession.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        }
        return homeService.page(null, model, page, size);
    }

    @GetMapping("/searchFlight")
    public String searchBooks(@RequestParam("term") String term, Model model,
                              @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        if (term.isBlank()) {
            return "redirect:/";
        }
        return homeService.page(term, model, page, size);
    }
}
