package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.Enum.EOrdersStatus;
import com.codecrafters.task_management_api.Enum.EPaymentMethods;
import com.codecrafters.task_management_api.dtos.OrderRecordDto;
import com.codecrafters.task_management_api.dtos.TicketRecordDto;
import com.codecrafters.task_management_api.models.OrdersModel;
import com.codecrafters.task_management_api.models.TicketManager;
import com.codecrafters.task_management_api.repositories.OrdersRepository;
import com.codecrafters.task_management_api.repositories.TicketManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TicketManagerRepository ticketManagerRepository;

    @Autowired
    private TicketsManagerService ticketsManagerService;


    public OrdersModel createOrder(OrderRecordDto orderRecordDto) {
        TicketManager ticketManager = ticketManagerRepository.findByEventModel_Id(orderRecordDto.ticketRecordDto().eventId())
                .orElseThrow(() -> new IllegalArgumentException("TicketManager not found for event ID: " + orderRecordDto.ticketRecordDto().eventId()));

        OrdersModel order = new OrdersModel();
        //order.setUserId(userId);
        order.setTicketManager(ticketManager);
        order.setTotalAmount(orderRecordDto.ticketRecordDto().price().multiply(BigDecimal.valueOf(orderRecordDto.ticketRecordDto().ticketSeat().size())));
        order.setOrderStatus(EOrdersStatus.Pending);
        order.setPaymentMethods(orderRecordDto.paymentMethods());
        order.setQuantityTickets(orderRecordDto.ticketRecordDto().ticketSeat().size());

        ordersRepository.save(order);

        TicketRecordDto ticketRecordDto = new TicketRecordDto(
                orderRecordDto.ticketRecordDto().eventId(),
                orderRecordDto.ticketRecordDto().ticketSeat(),
                ticketManager.getId(),
                orderRecordDto.ticketRecordDto().batchNumber(),
                orderRecordDto.ticketRecordDto().price(),
                new Date()
        );

        ticketsManagerService.purchaseTicket(ticketRecordDto);

        order.setOrderStatus(EOrdersStatus.Completed);
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
