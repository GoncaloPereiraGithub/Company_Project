package com.company.service;

import com.company.dto.UserRegistrationDto;
import com.company.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    // Added to findByEmail, retrieve error on already existing email
    User findUserByEmail(String email);

}
