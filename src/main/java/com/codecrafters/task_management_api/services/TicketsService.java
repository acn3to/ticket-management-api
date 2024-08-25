package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.models.EventModel;
import com.codecrafters.task_management_api.models.Tickets;
import com.codecrafters.task_management_api.repositories.EventRepository;
import com.codecrafters.task_management_api.repositories.TicketManagerRepository;
import com.codecrafters.task_management_api.repositories.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TicketsService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TicketManagerRepository ticketManagerRepository;

    @Autowired
    private EventRepository eventRepository;

    private boolean hasEvent(UUID eventId){
        return eventRepository.findEventById(eventId).isPresent();
    }

    public void setBachTicketPrice(UUID eventId, Integer batchNumber, BigDecimal price){
        if (!hasEvent(eventId)){
            throw new RuntimeException("Event not found ");
        }
        List<Tickets> tickets = ticketsRepository.findByEventModel_Id(eventId);
        for (Tickets ticket:tickets){
            ticket.setBatchNumber(batchNumber);
            ticket.setPrice(price);
        }
        ticketsRepository.saveAll(tickets);
    }

}
