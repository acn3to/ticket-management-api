package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.Enum.EOrdersStatus;
import com.codecrafters.task_management_api.Enum.EPaymentMethods;
import com.codecrafters.task_management_api.dtos.TicketRecordDto;
import com.codecrafters.task_management_api.models.OrdersModel;
import com.codecrafters.task_management_api.models.TicketManager;
import com.codecrafters.task_management_api.models.UserModel;
import com.codecrafters.task_management_api.repositories.OrdersRepository;
import com.codecrafters.task_management_api.repositories.TicketManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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


    public OrdersModel createOrder(UUID userId, UUID eventId, Map<UUID, String> ticketSeatMap, Integer batchNumber, BigDecimal price, EPaymentMethods paymentMethod) {
        TicketManager ticketManager = ticketManagerRepository.findByEventModel_Id(eventId)
                .orElseThrow(() -> new IllegalArgumentException("TicketManager not found for event ID: " + eventId));

        OrdersModel order = new OrdersModel();
        //order.setUserId(userId);
        order.setTicketManager(ticketManager);
        order.setTotalAmount(price.multiply(BigDecimal.valueOf(ticketSeatMap.size())));
        order.setOrderStatus(EOrdersStatus.Pending);
        order.setPaymentMethods(paymentMethod);
        order.setQuantityTickets(ticketSeatMap.size());

        ordersRepository.save(order);

        TicketRecordDto ticketRecordDto = new TicketRecordDto(
                eventId,
                ticketSeatMap,
                ticketManager.getId(),
                batchNumber,
                price,
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
