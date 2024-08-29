package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.RatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<RatingModel, UUID>, JpaSpecificationExecutor<RatingModel> {
}