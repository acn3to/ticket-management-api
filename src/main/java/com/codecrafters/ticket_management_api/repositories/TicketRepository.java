package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.enums.TicketStatusEnum;
import com.codecrafters.ticket_management_api.models.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel, UUID>, JpaSpecificationExecutor<TicketModel> {
    List<TicketModel> findByEventId(UUID eventId);
    List<TicketModel> findByTicketStatus(TicketStatusEnum ticketStatus);
}
