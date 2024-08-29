package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.models.ActivityLogModel;
import com.codecrafters.ticket_management_api.models.UserModel;
import com.codecrafters.ticket_management_api.repositories.ActivityLogRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import com.codecrafters.ticket_management_api.dto.ActivityLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    public ActivityLogModel createActivityLog(ActivityLogDTO activityLogDTO) {
        try {
            UserModel user = userRepository.findById(activityLogDTO.userId())
                    .orElseThrow(() -> new CustomException("User not found with ID: " + activityLogDTO.userId()));

            ActivityLogModel activityLog = ActivityLogModel.builder()
                    .user(user)
                    .activity(activityLogDTO.activity())
                    .ipAddress(activityLogDTO.ipAddress())
                    .build();

            return activityLogRepository.save(activityLog);
        } catch (Exception e) {
            throw new CustomException("Error creating activity log: " + e.getMessage());
        }
    }

    public List<ActivityLogModel> getAllActivityLogs() {
        try {
            return activityLogRepository.findAll();
        } catch (Exception e) {
            throw new CustomException("Error retrieving activity logs: " + e.getMessage());
        }
    }

    public ActivityLogModel getActivityLogById(UUID id) {
        return activityLogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Activity log not found with ID: " + id));
    }
}