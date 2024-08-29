package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.models.ActivityLogModel;
import com.codecrafters.ticket_management_api.services.ActivityLogService;
import com.codecrafters.ticket_management_api.dto.ActivityLogDTO;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @PostMapping
    public ResponseEntity<ActivityLogModel> createActivityLog(@RequestBody ActivityLogDTO activityLogDTO) {
        try {
            ActivityLogModel createdLog = activityLogService.createActivityLog(activityLogDTO);
            return ResponseEntity.status(201).body(createdLog);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ActivityLogModel>> getAllActivityLogs() {
        try {
            List<ActivityLogModel> logs = activityLogService.getAllActivityLogs();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLogModel> getActivityLogById(@PathVariable UUID id) {
        try {
            ActivityLogModel log = activityLogService.getActivityLogById(id);
            return ResponseEntity.ok(log);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
