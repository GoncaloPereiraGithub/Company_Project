package com.company.service;

import com.company.dto.UserRegistrationDto;
import com.company.model.Role;
import com.company.model.User;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    // Constructor Injection
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override // Saving User passed with DTO to DB
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }

}
