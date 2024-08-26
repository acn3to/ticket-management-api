package com.codecrafters.task_management_api.controllers;

import com.codecrafters.task_management_api.Enum.EPaymentMethods;
import com.codecrafters.task_management_api.dtos.OrderRecordDto;
import com.codecrafters.task_management_api.models.OrdersModel;
import com.codecrafters.task_management_api.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    @PostMapping("/create")
    public ResponseEntity<OrdersModel> createOrder(
            @RequestBody OrderRecordDto orderRecordDto
    ) {
        try {
            OrdersModel order = ordersService.createOrder(orderRecordDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId) {
        try {
            ordersService.cancelOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order cancelled successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reserve/{orderId}")
    public ResponseEntity<String> reserveOrder(@PathVariable UUID orderId) {
        try {
            ordersService.reserveOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order reserved successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/available/{orderId}")
    public ResponseEntity<Boolean> areTicketsAvailable(@PathVariable UUID orderId) {
        try {
            boolean available = ordersService.areTicketsAvailable(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(available);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
