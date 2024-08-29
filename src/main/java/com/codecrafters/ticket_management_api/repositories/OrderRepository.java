package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID>, JpaSpecificationExecutor<OrderModel> {
}
