package com.codecrafters.task_management_api.controllers;

import com.codecrafters.task_management_api.dtos.TicketRecordDto;
import com.codecrafters.task_management_api.models.TicketsModel;
import com.codecrafters.task_management_api.services.TicketsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {

    @Autowired
    private TicketsManagerService ticketsManagerService;

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestBody TicketRecordDto ticketRecordDto) {
        try {
            ticketsManagerService.purchaseTicket(ticketRecordDto);
            return ResponseEntity.status(HttpStatus.OK).body("Tickets purchased successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable UUID ticketId) {
        try {
            ticketsManagerService.cancelTicket(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body("Ticket cancelled successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reserve/{ticketId}")
    public ResponseEntity<String> reserveTicket(@PathVariable UUID ticketId) {
        try {
            ticketsManagerService.reserveTicket(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body("Ticket reserved successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/available/{ticketId}")
    public ResponseEntity<Boolean> isTicketAvailable(@PathVariable UUID ticketId) {
        try {
            boolean available = ticketsManagerService.isTicketAvailable(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body(available);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @GetMapping("/details/{ticketId}")
    public ResponseEntity<TicketsModel> printTicketsDetails(@PathVariable UUID ticketId) {
        try {
            TicketsModel ticket = ticketsManagerService.printTicketsDetails(ticketId);
            return ResponseEntity.status(HttpStatus.OK).body(ticket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
