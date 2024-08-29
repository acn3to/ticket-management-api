package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.AuthenticationDTO;
import com.codecrafters.ticket_management_api.dto.CreateUserDTO;
import com.codecrafters.ticket_management_api.dto.LoginResponseDTO;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import com.codecrafters.ticket_management_api.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        try {
            LoginResponseDTO response = authenticationService.login(authenticationDTO);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            authenticationService.register(createUserDTO);
            return ResponseEntity.status(201).build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}