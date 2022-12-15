package com.company.service;

import com.company.dto.UserRegistrationDto;
import com.company.model.User;
import org.springframework.data.convert.DtoInstantiatingConverter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.Column;

@Service
public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
