package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.dto.AuthenticationDTO;
import com.codecrafters.ticket_management_api.dto.CreateUserDTO;
import com.codecrafters.ticket_management_api.dto.LoginResponseDTO;
import com.codecrafters.ticket_management_api.enums.UserRoleEnum;
import com.codecrafters.ticket_management_api.models.UserModel;
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
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((UserModel) auth.getPrincipal());
            return new LoginResponseDTO(token);
        } catch (Exception e) {
            throw new CustomException("Invalid username or password");
        }
    }

    @Transactional
    public void register(@Valid CreateUserDTO data) {
        if (userRepository.findByUsername(data.username()) != null) {
            throw new CustomException("Username is already taken");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserModel newUser = UserModel.builder()
                .id(UUID.randomUUID())
                .username(data.username())
                .name(data.name())
                .email(data.email())
                .age(data.age())
                .password(encryptedPassword)
                .role(UserRoleEnum.valueOf(data.role()))
                .address(data.address())
                .isActive(true)
                .createdAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();

        userRepository.save(newUser);
    }
}