package com.codecrafters.ticket_management_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "event_id",nullable = false)
    private EventModel eventModel;

    @OneToMany(mappedBy = "ticketManager",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrdersModel> orders;

    @OneToMany(mappedBy = "ticketManager",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TicketsModel> tickets;

    private Integer ticketsQuantity;

}
