package com.codecrafters.ticket_management_api.specs;

import com.codecrafters.ticket_management_api.models.TicketModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Date;

public class TicketSpecifications {

    public static Specification<TicketModel> hasEventId(Long eventId) {
        return (root, query, criteriaBuilder) -> eventId == null ? null
                : criteriaBuilder.equal(root.get("eventModel").get("id"), eventId);
    }

    public static Specification<TicketModel> hasTicketId(Long ticketId) {
        return (root, query, criteriaBuilder) -> ticketId == null ? null
                : criteriaBuilder.equal(root.get("id"), ticketId);
    }

    public static Specification<TicketModel> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return null;
        };
    }

    public static Specification<TicketModel> hasSeatNumber(String seatNumber) {
        return (root, query, criteriaBuilder) -> seatNumber == null ? null
                : criteriaBuilder.like(root.get("seatNumber"), "%" + seatNumber + "%");
    }

    public static Specification<TicketModel> wasPurchasedAfter(Date afterDate) {
        return (root, query, criteriaBuilder) -> afterDate == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("purchaseDate"), afterDate);
    }

    public static Specification<TicketModel> wasPurchasedBefore(Date beforeDate) {
        return (root, query, criteriaBuilder) -> beforeDate == null ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("purchaseDate"), beforeDate);
    }

    public static Specification<TicketModel> hasTicketStatus(String ticketStatus) {
        return (root, query, criteriaBuilder) -> ticketStatus == null ? null
                : criteriaBuilder.like(root.get("ticketStatus"), "%" + ticketStatus + "%");
    }
}
