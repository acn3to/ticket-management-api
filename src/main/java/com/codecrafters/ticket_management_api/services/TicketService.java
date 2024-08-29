package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.enums.TicketStatusEnum;
import com.codecrafters.ticket_management_api.dto.TicketDTO;
import com.codecrafters.ticket_management_api.models.TicketModel;
import com.codecrafters.ticket_management_api.repositories.TicketRepository;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import com.codecrafters.ticket_management_api.specs.TicketSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<TicketModel> getAllTickets(
            Long eventId,
            String seatNumber,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Date purchasedAfter,
            Date purchasedBefore,
            String ticketStatus) {
        try {
            Specification<TicketModel> spec = Specification
                    .where(TicketSpecifications.hasEventId(eventId))
                    .and(TicketSpecifications.hasSeatNumber(seatNumber))
                    .and(TicketSpecifications.hasPriceBetween(minPrice, maxPrice))
                    .and(TicketSpecifications.wasPurchasedAfter(purchasedAfter))
                    .and(TicketSpecifications.wasPurchasedBefore(purchasedBefore))
                    .and(TicketSpecifications.hasTicketStatus(ticketStatus));

            return ticketRepository.findAll(spec);
        } catch (Exception e) {
            throw new CustomException("Error retrieving tickets: " + e.getMessage());
        }
    }

    public TicketModel createTicket(TicketDTO data) {
        try {
            var event = eventRepository.findEventById(data.eventId())
                    .orElseThrow(() -> new CustomException("Event not found"));

            TicketModel newTicket = TicketModel.builder()
                    .event(event)
                    .batchNumber(data.batchNumber())
                    .price(data.price())
                    .seatNumber(data.ticketSeat().get(event.getId()))
                    .purchaseDate(data.purchaseDate())
                    .ticketStatus(TicketStatusEnum.SOLD)
                    .qrCode(generateQrCode())
                    .build();

            return ticketRepository.save(newTicket);
        } catch (Exception e) {
            throw new CustomException("Error creating ticket: " + e.getMessage());
        }
    }

    public List<TicketModel> getTicketsByEventId(UUID eventId) {
        try {
            return ticketRepository.findByEvent_Id(eventId);
        } catch (Exception e) {
            throw new CustomException("Error retrieving tickets by event ID: " + e.getMessage());
        }
    }

    public List<TicketModel> getTicketsByStatus(TicketStatusEnum status) {
        try {
            return ticketRepository.findByTicketStatus(status);
        } catch (Exception e) {
            throw new CustomException("Error retrieving tickets by status: " + e.getMessage());
        }
    }

    public Optional<TicketModel> getTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId);
    }

    private String generateQrCode() {
        return "generated-qr-code";
    }
}
