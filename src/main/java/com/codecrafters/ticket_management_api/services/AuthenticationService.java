package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.dto.AuthenticationDTO;
import com.codecrafters.ticket_management_api.dto.CreateUserDTO;
import com.codecrafters.ticket_management_api.dto.LoginResponseDTO;
import com.codecrafters.ticket_management_api.enums.UserRole;
import com.codecrafters.ticket_management_api.models.User;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codecrafters.ticket_management_api.exceptions.CustomException;

import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(@Valid AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    @Transactional
    public void register(@Valid CreateUserDTO data) {

        if (userRepository.findByUsername(data.username()) != null) {
            throw new CustomException("Username is already taken");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = User.builder()
                .id(UUID.randomUUID())
                .username(data.username())
                .name(data.name())
                .email(data.email())
                .age(data.age())
                .password(encryptedPassword)
                .role(UserRole.valueOf(data.role()))
                .address(data.address())
                .isActive(true)
                .createdAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();

        userRepository.save(newUser);
    }
}