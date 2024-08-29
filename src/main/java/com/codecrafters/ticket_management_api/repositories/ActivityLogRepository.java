package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.ActivityLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogModel, UUID> {
}