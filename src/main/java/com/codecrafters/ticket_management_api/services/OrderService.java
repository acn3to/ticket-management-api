package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.enums.OrderStatusEnum;
import com.codecrafters.ticket_management_api.dto.OrderDTO;
import com.codecrafters.ticket_management_api.models.OrderModel;
import com.codecrafters.ticket_management_api.models.UserModel;
import com.codecrafters.ticket_management_api.repositories.OrderRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import com.codecrafters.ticket_management_api.specs.OrderSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderModel> getAllOrders(
            UUID orderId, Date beforeDate,
            Date afterDate,
            String orderStatus,
            String paymentMethod) {
        try {
            Specification<OrderModel> spec = Specification
                    .where(OrderSpecifications.hasOrderId(orderId))
                    .and(OrderSpecifications.wasOrderedBefore(beforeDate))
                    .and(OrderSpecifications.wasOrderedAfter(afterDate))
                    .and(OrderSpecifications.hasOrderStatus(orderStatus))
                    .and(OrderSpecifications.hasPaymentMethod(paymentMethod));

            return ordersRepository.findAll(spec);
        } catch (Exception e) {
            throw new CustomException("Error retrieving orders: " + e.getMessage());
        }
    }

    public OrderModel createOrder(OrderDTO data) {
        try {
            UserModel user = userRepository.findById(data.userId())
                    .orElseThrow(() -> new CustomException("User not found with ID: " + data.userId()));

            OrderModel newOrder = OrderModel.builder()
                    .user(user)
                    .totalAmount(data.totalAmount())
                    .orderStatus(OrderStatusEnum.COMPLETED)
                    .paymentMethods(data.paymentMethod())
                    .build();

            return ordersRepository.save(newOrder);
        } catch (Exception e) {
            throw new CustomException("Error creating order: " + e.getMessage());
        }
    }

    public void cancelOrder(UUID orderId) {
        try {
            OrderModel order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException("Order not found with ID: " + orderId));

            if (order.getOrderStatus() == OrderStatusEnum.COMPLETED) {
                throw new CustomException("Cannot cancel a completed order.");
            }

            order.setOrderStatus(OrderStatusEnum.CANCELLED);
            ordersRepository.save(order);
        } catch (Exception e) {
            throw new CustomException("Error cancelling order: " + e.getMessage());
        }
    }

    public void reserveOrder(UUID orderId) {
        try {
            OrderModel order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException("Order not found with ID: " + orderId));

            order.setOrderStatus(OrderStatusEnum.PENDING);
            ordersRepository.save(order);
        } catch (Exception e) {
            throw new CustomException("Error reserving order: " + e.getMessage());
        }
    }

    public boolean areTicketsAvailable(UUID orderId) {
        try {
            ordersRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException("Order not found with ID: " + orderId));
            return true;
        } catch (Exception e) {
            throw new CustomException("Error checking ticket availability: " + e.getMessage());
        }
    }
}
