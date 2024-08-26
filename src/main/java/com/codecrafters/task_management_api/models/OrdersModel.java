package com.codecrafters.task_management_api.models;

import com.codecrafters.task_management_api.Enum.EOrdersStatus;
import com.codecrafters.task_management_api.Enum.EPaymentMethods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "orders",  indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_order_status", columnList = "order_status"),
        @Index(name = "idx_order_date", columnList = "order_date")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_Id",nullable = false)
    private UserModel userId;

    @ManyToOne
    @JoinColumn(name = "ticket_manager_id", nullable = false)
    private TicketManager ticketManager;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private EOrdersStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private EPaymentMethods paymentMethods;

    private Integer quantityTickets;

    @CreationTimestamp
    private Date createOrderDate;

    @UpdateTimestamp
    private Date updateOrderDate;

}
