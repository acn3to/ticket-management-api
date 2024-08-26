package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.Enum.ETicketsStatus;
import com.codecrafters.task_management_api.dtos.TicketRecordDto;
import com.codecrafters.task_management_api.models.EventModel;
import com.codecrafters.task_management_api.models.TicketManager;
import com.codecrafters.task_management_api.models.TicketsModel;
import com.codecrafters.task_management_api.repositories.EventRepository;
import com.codecrafters.task_management_api.repositories.TicketManagerRepository;
import com.codecrafters.task_management_api.repositories.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketsManagerService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TicketManagerRepository ticketManagerRepository;

    @Autowired
    private EventRepository eventRepository;


    public void purchaseTicket(TicketRecordDto ticketRecordDto) {
        EventModel event = eventRepository.findById(ticketRecordDto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + ticketRecordDto.eventId()));

        TicketManager ticketManager = ticketManagerRepository.findById(ticketRecordDto.ticketManagerId())
                .orElseThrow(() -> new IllegalArgumentException("TicketManager not found with ID: " + ticketRecordDto.ticketManagerId()));

        Map<UUID,String> ticketAndSeat = ticketRecordDto.ticketSeat();
        if (ticketManager.getTicketsQuantity()<ticketAndSeat.size()){
            throw new IllegalStateException("Not enough tickets available.");
        }
        for (Map.Entry<UUID,String> entry:ticketAndSeat.entrySet()){
            UUID ticketId = entry.getKey();
            String seatNumber = entry.getValue();

            Optional<TicketsModel> ticketOptional = ticketsRepository.findById(ticketId);
            if (ticketOptional.isPresent()){
                TicketsModel ticket = ticketOptional.get();
                if (ticket.getTicketStatus() == ETicketsStatus.Sold){
                    throw new IllegalStateException("Ticket with id "+ ticketId+" has already been sold.");
                }
                if (ticket.getTicketStatus() == ETicketsStatus.Reserved){
                    throw new IllegalStateException("Ticket with id "+ ticketId+" cannot be sold because it is reserved.");
                }
                ticket.setBatchNumber(ticketRecordDto.batchNumber());
                ticket.setPrice(ticketRecordDto.price());
                ticket.setSeatNumber(seatNumber);
                ticket.setTicketStatus(ETicketsStatus.Sold);
                ticket.setPurchaseDate(ticketRecordDto.purchaseDate());

                ticketsRepository.save(ticket);
            }else {
                throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
            }
        }
        ticketManager.setTicketsQuantity(ticketManager.getTicketsQuantity() - ticketAndSeat.size());
        ticketManagerRepository.save(ticketManager);
    }

    public void cancelTicket(UUID ticketId) {
        Optional<TicketsModel> ticketOptional = ticketsRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            TicketsModel ticket = ticketOptional.get();

            if (ticket.getTicketStatus() == ETicketsStatus.Sold) {
                throw new IllegalStateException("Cannot cancel a ticket that has already been sold.");
            }

            ticket.setTicketStatus(ETicketsStatus.Cancelled);

            TicketManager ticketManager = ticket.getTicketManager();
            ticketManager.setTicketsQuantity(ticketManager.getTicketsQuantity() + 1);

            ticketsRepository.save(ticket);
            ticketManagerRepository.save(ticketManager);
        } else {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }
    }

    public void reserveTicket(UUID ticketId) {
        Optional<TicketsModel> ticketOptional = ticketsRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            TicketsModel ticket = ticketOptional.get();

            if (ticket.getTicketStatus() == ETicketsStatus.Sold || ticket.getTicketStatus() == ETicketsStatus.Reserved) {
                throw new IllegalStateException("Ticket with ID " + ticketId + " cannot be reserved.");
            }

            ticket.setTicketStatus(ETicketsStatus.Reserved);

            ticketsRepository.save(ticket);
        } else {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }
    }

    public boolean isTicketAvailable(UUID ticketId) {
        Optional<TicketsModel> ticketOptional = ticketsRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            TicketsModel ticket = ticketOptional.get();
            return ticket.getTicketStatus() == ETicketsStatus.Available;
        } else {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }
    }


    public TicketsModel printTicketsDetails(UUID ticketId){
        Optional<TicketsModel> ticketsOptional = ticketsRepository.findById(ticketId);
        if (ticketsOptional.isPresent()){
            TicketsModel ticket = ticketsOptional.get();
            EventModel event = ticket.getEventModel();

            System.out.println("Ticket Details");
            System.out.println("Event Name: ");
            System.out.println("Location: ");
            System.out.println("Date: ");
            System.out.println("Time: ");
            System.out.println("Batch Number: " + ticket.getBatchNumber());
            System.out.println("Seat Number: "+ ticket.getSeatNumber());

            return ticket;
        }else {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }
    }

}
