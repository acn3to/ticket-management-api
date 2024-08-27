package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.AuthenticationDTO;
import com.codecrafters.ticket_management_api.dto.CreateUserDTO;
import com.codecrafters.ticket_management_api.dto.LoginResponseDTO;
import com.codecrafters.ticket_management_api.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authRequest) {
    System.out.println("Received login request: " + authRequest);
    return ResponseEntity.ok(authenticationService.login(authRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid CreateUserDTO data) {
    authenticationService.register(data);
    return ResponseEntity.ok().build();
  }
}