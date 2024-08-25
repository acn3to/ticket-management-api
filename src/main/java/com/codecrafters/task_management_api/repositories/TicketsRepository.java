package com.codecrafters.task_management_api.repositories;

import com.codecrafters.task_management_api.models.Tickets;
import com.codecrafters.task_management_api.specifications.TicketsSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, UUID>, JpaSpecificationExecutor<Tickets> {
    default Long countTicketByOrderEvent(Long eventId) {
         return count(TicketsSpecifications.queryFindSimilarTickets(eventId));
    }
    List<Tickets> findByEventModel_Id(UUID eventId);
}
