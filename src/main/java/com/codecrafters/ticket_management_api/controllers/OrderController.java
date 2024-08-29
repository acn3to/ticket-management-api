package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.OrderDTO;
import com.codecrafters.ticket_management_api.models.OrderModel;
import com.codecrafters.ticket_management_api.services.OrderService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService ordersService;

    @PostMapping("/create")
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderModel order = ordersService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId) {
        try {
            ordersService.cancelOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order cancelled successfully.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error cancelling order.");
        }
    }

    @PostMapping("/reserve/{orderId}")
    public ResponseEntity<String> reserveOrder(@PathVariable UUID orderId) {
        try {
            ordersService.reserveOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order reserved successfully.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reserving order.");
        }
    }

    @GetMapping("/available/{orderId}")
    public ResponseEntity<Boolean> areTicketsAvailable(@PathVariable UUID orderId) {
        try {
            boolean available = ordersService.areTicketsAvailable(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(available);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
