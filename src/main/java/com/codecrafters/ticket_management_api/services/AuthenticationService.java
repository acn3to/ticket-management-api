package com.codecrafters.ticket_management_api.services;


import com.codecrafters.ticket_management_api.Enum.UserRole;
import com.codecrafters.ticket_management_api.dtos.AuthenticationDTO;
import com.codecrafters.ticket_management_api.dtos.CreateUserDTO;
import com.codecrafters.ticket_management_api.dtos.LoginResponseDTO;
import com.codecrafters.ticket_management_api.models.User;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println("Attempting to log in user: " + data.username());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        System.out.println("User authenticated successfully: " + data.username());

        var token = tokenService.generateToken((User) auth.getPrincipal());
        System.out.println("Generating token for user: " + data.username());

        return new LoginResponseDTO(token);
    }

    @Transactional
    public void register(@Valid CreateUserDTO data) {
        System.out.println("Attempting to register user: " + data.username());

        if (userRepository.findByUsername(data.username()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }

        System.out.println("Username check passed for: " + data.username());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        System.out.println("Encrypting password for user: " + data.username());

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

        System.out.println("User object created: " + newUser);
        System.out.println("Role set for user: " + newUser.getRole());
        System.out.println("Saving user to the database: " + newUser.getUsername());

        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
}