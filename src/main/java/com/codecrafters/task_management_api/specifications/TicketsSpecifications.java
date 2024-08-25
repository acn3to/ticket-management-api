package com.codecrafters.task_management_api.specifications;

import com.codecrafters.task_management_api.models.Tickets;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketsSpecifications {
    public Specification<Tickets> findTicketBy(
            Long eventId,
            Long ticketId,
            BigDecimal maxPrice,
            BigDecimal minPrice,
            String seatNumber,
            Date beforeDate,
            Date afterDate,
            String ticketStatus
    ){
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(eventId!= null){
                predicates.add(criteriaBuilder.equal(root.get("eventId"),eventId));
            }
            if (ticketId!=null){
                predicates.add(criteriaBuilder.equal(root.get("id"),ticketId));
            }
            if (minPrice != null && maxPrice != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
            } else {
                if (minPrice != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
                }
                if (maxPrice != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
                }
            }
            if (!seatNumber.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("seatNumber"),seatNumber));
            }
            if (beforeDate != null && afterDate != null) {
                predicates.add(criteriaBuilder.between(root.get("purchaseDate"), afterDate, beforeDate));
            } else {
                if (beforeDate != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("purchaseDate"), beforeDate));
                }
                if (afterDate != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("purchaseDate"), afterDate));
                }
            }
            if (!ticketStatus.isEmpty() || ticketStatus!=null){
                predicates.add(criteriaBuilder.like(root.get("ticketStatus"),ticketStatus));
            }

            assert query != null;
            query.orderBy(criteriaBuilder.desc(root));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
    }

    public static Specification<Tickets> queryFindSimilarTickets(
            Long eventId
    ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (eventId!=null){
                Join<Object, Object> eventJoin = root.join("eventModel", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(eventJoin.get("id"), eventId));            }
            assert query != null;
            query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));
            return query.getRestriction();
        });
    }
}


