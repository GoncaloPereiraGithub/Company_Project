package com.company.controller;

import com.company.dto.UserRegistrationDto;
import com.company.model.User;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;

    // Constructor based Dependency Injection
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user") // Submitting successfully a new user, leads to userRegistrationDto
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping // Displays registration html
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping // Obj user, gets UI params
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {

        // If email is already registered in Database, return error
        User emailEntryCheck = userRepo.findByEmail(registrationDto.getEmail());
        if (emailEntryCheck != null){
            // Testing
//            System.out.println("*********** Email found = " + emailEntryCheck.getFirstName() + " "  + emailEntryCheck.getLastName() + " " + emailEntryCheck.getEmail());
            return "redirect:/registration?error"; // Return a view(Registration page with error param)
        }

        // Saving user
        userService.save(registrationDto);
        return "redirect:/registration?success"; // Return a view(Registration page with success param)
    }

}
