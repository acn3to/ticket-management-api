package com.codecrafters.ticket_management_api.models;

import com.codecrafters.ticket_management_api.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_event_id", columnList = "event_id"),
        @Index(name = "idx_ticket_status", columnList = "ticket_status"),
        @Index(name = "idx_purchase_date", columnList = "purchase_date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventModel event;

    @Column(name = "batch_number", nullable = false)
    private Integer batchNumber;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "purchase_date", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false, length = 50)
    private TicketStatusEnum ticketStatus;

    @Column(name = "qr_code", nullable = false, length = 255)
    private String qrCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
