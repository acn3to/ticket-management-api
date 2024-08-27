package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.Enum.EOrdersStatus;
import com.codecrafters.ticket_management_api.dtos.OrderRecordDto;
import com.codecrafters.ticket_management_api.dtos.TicketRecordDto;
import com.codecrafters.ticket_management_api.models.OrdersModel;
import com.codecrafters.ticket_management_api.models.TicketManager;
import com.codecrafters.ticket_management_api.models.TicketsModel;
import com.codecrafters.ticket_management_api.models.User;
import com.codecrafters.ticket_management_api.repositories.OrdersRepository;
import com.codecrafters.ticket_management_api.repositories.TicketManagerRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TicketManagerRepository ticketManagerRepository;

    @Autowired
    private TicketsManagerService ticketsManagerService;

    @Autowired
    private UserRepository userRepository;


    public OrdersModel createOrder(OrderRecordDto orderRecordDto) {

        OrdersModel order = new OrdersModel();

        TicketRecordDto ticketRecordDto = new TicketRecordDto(
                orderRecordDto.ticketRecordDto().eventId(),
                orderRecordDto.ticketRecordDto().ticketSeat(),
                orderRecordDto.ticketRecordDto().batchNumber(),
                orderRecordDto.ticketRecordDto().price(),
                new Date()
        );

        TicketManager ticketManager = ticketsManagerService.purchaseTicket(ticketRecordDto);

        order.setTicketManager(ticketManager);
        order.setTotalAmount(orderRecordDto.ticketRecordDto().price().multiply(BigDecimal.valueOf(orderRecordDto.ticketRecordDto().ticketSeat().size())));
        order.setOrderStatus(EOrdersStatus.Completed);
        order.setPaymentMethods(orderRecordDto.paymentMethods());
        order.setQuantityTickets(orderRecordDto.ticketRecordDto().ticketSeat().size());

        ordersRepository.save(order);

        return order;
    }


    public void cancelOrder(UUID orderId) {
        OrdersModel order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        if (order.getOrderStatus() == EOrdersStatus.Completed) {
            throw new IllegalStateException("Cannot cancel a completed order.");
        }

        order.getTicketManager().getTickets().forEach(ticket -> {
            ticketsManagerService.cancelTicket(ticket.getId());
        });

        order.setOrderStatus(EOrdersStatus.Cancelled);
        ordersRepository.save(order);
    }

    public void reserveOrder(UUID orderId) {
        OrdersModel order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        order.getTicketManager().getTickets().forEach(ticket -> {
            ticketsManagerService.reserveTicket(ticket.getId());
        });

        order.setOrderStatus(EOrdersStatus.Pending);
        ordersRepository.save(order);
    }


    public boolean areTicketsAvailable(UUID orderId) {
        OrdersModel order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        return order.getTicketManager().getTickets().stream().allMatch(ticket ->
                ticketsManagerService.isTicketAvailable(ticket.getId())
        );
    }
}
