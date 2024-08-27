package com.codecrafters.ticket_management_api.specifications;

import com.codecrafters.ticket_management_api.Enum.EventCategory;
import com.codecrafters.ticket_management_api.models.EventModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventSpecification {

    public static Specification<EventModel> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<EventModel> hasCategory(EventCategory category) {
        return (root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<EventModel> hasOrganizerId(UUID organizerId) {
        return (root, query, criteriaBuilder) ->
                organizerId == null ? null : criteriaBuilder.equal(root.get("organizerId"), organizerId);
    }

    public static Specification<EventModel> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<EventModel> hasStartDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                (startDate == null || endDate == null) ? null : criteriaBuilder.between(root.get("startDate"), startDate, endDate);
    }

    public static Specification<EventModel> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                (minPrice == null || maxPrice == null) ? null : criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<EventModel> hasMaxCapacityGreaterThanOrEqual(Integer capacity) {
        return (root, query, criteriaBuilder) ->
                capacity == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("maxCapacity"), capacity);
    }

    public static Specification<EventModel> hasRemainingCapacityGreaterThanOrEqual(Integer remainingCapacity) {
        return (root, query, criteriaBuilder) ->
                remainingCapacity == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("remainingCapacity"), remainingCapacity);
    }

    public static Specification<EventModel> isCreatedAfter(LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) ->
                createdAt == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }

    public static Specification<EventModel> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                location == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

}
