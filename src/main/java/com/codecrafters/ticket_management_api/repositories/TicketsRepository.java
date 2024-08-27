package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.TicketsModel;
import com.codecrafters.ticket_management_api.specifications.TicketsSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketsRepository extends JpaRepository<TicketsModel, UUID>, JpaSpecificationExecutor<TicketsModel> {
    default Long countTicketByOrderEvent(Long eventId) {
         return count(TicketsSpecifications.queryFindSimilarTickets(eventId));
    }
    List<TicketsModel> findByEventModel_Id(UUID eventId);
}
