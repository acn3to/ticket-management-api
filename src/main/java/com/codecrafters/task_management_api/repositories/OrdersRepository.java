package com.codecrafters.task_management_api.repositories;

import com.codecrafters.task_management_api.models.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersModel, UUID>, JpaSpecificationExecutor<OrdersModel> {
}
