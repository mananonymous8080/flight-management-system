package com.swetha.fms.controller;

import com.swetha.fms.enums.AuthorityType;
import com.swetha.fms.model.Authority;
import com.swetha.fms.model.User;
import com.swetha.fms.repository.AuthorityRepository;
import com.swetha.fms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    AuthorityRepository authorityRepository;
    @GetMapping("/register")
    public String registerPage() {
        return "registerUser";
    }
    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, RedirectAttributes redirect) {

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userService.saveUser(user);
        authorityRepository.save(new Authority(user.getUsername(), AuthorityType.USER.name()));
        return "redirect:/";
    }
}
