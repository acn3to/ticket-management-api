package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.TicketDTO;
import com.codecrafters.ticket_management_api.models.TicketModel;
import com.codecrafters.ticket_management_api.services.TicketService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketModel>> getAllTickets(
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) String seatNumber,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String ticketStatus) {
        try {
            List<TicketModel> tickets = ticketService.getAllTickets(eventId, seatNumber, minPrice, maxPrice, null, null,
                    ticketStatus);
            return ResponseEntity.ok(tickets);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<TicketModel> createTicket(@RequestBody TicketDTO ticketDTO) {
        try {
            TicketModel ticket = ticketService.createTicket(ticketDTO);
            return ResponseEntity.status(201).body(ticket);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketModel> getTicketById(@PathVariable UUID ticketId) {
        try {
            TicketModel ticket = ticketService.getTicketById(ticketId)
                    .orElseThrow(() -> new CustomException("Ticket not found"));
            return ResponseEntity.ok(ticket);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
