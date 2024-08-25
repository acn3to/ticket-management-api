package com.codecrafters.task_management_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket_manager")
@Getter
@Setter
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
    private List<Orders> orders;

    @OneToMany(mappedBy = "ticketManager",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tickets> tickets;

    private Integer ticketsQuantity;
}
