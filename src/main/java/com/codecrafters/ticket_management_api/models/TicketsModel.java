package com.codecrafters.ticket_management_api.models;

import com.codecrafters.ticket_management_api.Enum.ETicketsStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tickets",  indexes = {
        @Index(name = "idx_event_id", columnList = "event_id"),
        @Index(name = "idx_ticket_status", columnList = "ticket_status"),
        @Index(name = "idx_purchase_date", columnList = "purchase_date")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_Id", nullable = false)
    private EventModel eventModel;

    @ManyToOne
    @JoinColumn(name = "ticket_manager_id", nullable = false)
    private TicketManager ticketManager;

    private Integer batchNumber;

    private BigDecimal price;

    private String seatNumber;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Enumerated(EnumType.STRING)
    private ETicketsStatus ticketStatus;


    @CreationTimestamp
    private Date dateTicketCreated;

    @UpdateTimestamp
    private Date dateTicketUpdate;


}
