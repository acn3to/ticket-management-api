package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.models.OrderTicketsModel;
import com.codecrafters.ticket_management_api.models.OrderModel;
import com.codecrafters.ticket_management_api.models.TicketModel;
import com.codecrafters.ticket_management_api.repositories.OrderTicketsRepository;
import com.codecrafters.ticket_management_api.repositories.OrderRepository;
import com.codecrafters.ticket_management_api.repositories.TicketRepository;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import com.codecrafters.ticket_management_api.dto.OrderTicketsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderTicketsService {

    @Autowired
    private OrderTicketsRepository orderTicketsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public OrderTicketsModel createOrderTicket(OrderTicketsDTO orderTicketsDTO) {
        try {
            OrderModel order = orderRepository.findById(orderTicketsDTO.orderId())
                    .orElseThrow(() -> new CustomException("Order not found with ID: " + orderTicketsDTO.orderId()));

            TicketModel ticket = ticketRepository.findById(orderTicketsDTO.ticketId())
                    .orElseThrow(() -> new CustomException("Ticket not found with ID: " + orderTicketsDTO.ticketId()));

            OrderTicketsModel newOrderTicket = OrderTicketsModel.builder()
                    .order(order)
                    .ticket(ticket)
                    .quantity(orderTicketsDTO.quantity())
                    .build();

            return orderTicketsRepository.save(newOrderTicket);
        } catch (Exception e) {
            throw new CustomException("Error creating order ticket: " + e.getMessage());
        }
    }

    public List<OrderTicketsModel> getAllOrderTickets() {
        try {
            return orderTicketsRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving order tickets: " + e.getMessage());
        }
    }

    public OrderTicketsModel getOrderTicketById(UUID id) {
        return orderTicketsRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order ticket not found with ID: " + id));
    }
}
