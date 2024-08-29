package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> , JpaSpecificationExecutor<UserModel> {
    UserDetails findByUsername(String username);
}