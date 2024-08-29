package com.codecrafters.ticket_management_api.specs;

import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.models.RatingModel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RatingSpecifications {

    public static Specification<RatingModel> hasEvent(EventModel event) {
        return (root, query, criteriaBuilder) -> event == null ? null : criteriaBuilder.equal(root.get("event"), event);
    }

    public static Specification<RatingModel> hasRating(BigDecimal rating) {
        return (root, query, criteriaBuilder) -> rating == null ? null
                : criteriaBuilder.equal(root.get("rating"), rating);
    }

    public static Specification<RatingModel> hasRatingBetween(BigDecimal minRating, BigDecimal maxRating) {
        return (root, query, criteriaBuilder) -> (minRating == null || maxRating == null) ? null
                : criteriaBuilder.between(root.get("rating"), minRating, maxRating);
    }

    public static Specification<RatingModel> hasReviewContaining(String review) {
        return (root, query, criteriaBuilder) -> review == null ? null
                : criteriaBuilder.like(criteriaBuilder.lower(root.get("review")), "%" + review.toLowerCase() + "%");
    }

    public static Specification<RatingModel> isCreatedAfter(LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) -> createdAt == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }

    public static Specification<RatingModel> isCreatedBefore(LocalDateTime createdAt) {
        return (root, query, criteriaBuilder) -> createdAt == null ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), createdAt);
    }
}
