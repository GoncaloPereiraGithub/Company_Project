package com.company.controller;

import com.company.dto.UserRegistrationDto;
import com.company.model.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class UserRegistrationController {

    private UserService userService;

    // Constructor based Dependency Injection
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user") // Submitting successfully a new user, leads to userRegistrationDto
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping // Obj user, gets UI params
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
        return "redirect:/registration?success"; // Returning a view(to Registration page with a success message)
    }
}
