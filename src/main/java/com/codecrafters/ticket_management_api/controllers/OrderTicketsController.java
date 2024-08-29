package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.OrderTicketsDTO;
import com.codecrafters.ticket_management_api.models.OrderTicketsModel;
import com.codecrafters.ticket_management_api.services.OrderTicketsService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-tickets")
public class OrderTicketsController {

    @Autowired
    private OrderTicketsService orderTicketsService;

    @PostMapping
    public ResponseEntity<OrderTicketsModel> createOrderTicket(@RequestBody OrderTicketsDTO orderTicketsDTO) {
        try {
            OrderTicketsModel createdOrderTicket = orderTicketsService.createOrderTicket(orderTicketsDTO);
            return ResponseEntity.status(201).body(createdOrderTicket);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderTicketsModel>> getAllOrderTickets() {
        try {
            List<OrderTicketsModel> orderTickets = orderTicketsService.getAllOrderTickets();
            return ResponseEntity.ok(orderTickets);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTicketsModel> getOrderTicketById(@PathVariable UUID id) {
        try {
            OrderTicketsModel orderTicket = orderTicketsService.getOrderTicketById(id);
            return ResponseEntity.ok(orderTicket);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
