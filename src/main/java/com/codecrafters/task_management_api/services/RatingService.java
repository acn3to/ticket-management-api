package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.models.EventModel;
import com.codecrafters.task_management_api.models.RatingModel;
import com.codecrafters.task_management_api.repositories.RatingRepository;
import com.codecrafters.task_management_api.specifications.RatingSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<RatingModel> getAllRatings(
            EventModel event,
            UUID user,
            BigDecimal minRating,
            BigDecimal maxRating,
            String review,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore
    ) {
        Specification<RatingModel> spec = Specification
                .where(RatingSpecification.hasEvent(event))
//                .and(RatingSpecification.hasUser(user))
                .and(RatingSpecification.hasRatingBetween(minRating, maxRating))
                .and(RatingSpecification.hasReviewContaining(review))
                .and(RatingSpecification.isCreatedAfter(createdAfter))
                .and(RatingSpecification.isCreatedBefore(createdBefore));

        return ratingRepository.findAll(spec);
    }

    public void deleteRating(UUID ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    public RatingModel publishRating(RatingModel ratingModel) {
        // Validar se o ratingModel contém todos os campos obrigatórios
//        if (ratingModel.getRating() == null || ratingModel.getEvent() == null || ratingModel.getUserId() == null) {
//            throw new IllegalArgumentException("Rating, Event, and UserId are required");
//        }
        // Salvar e retornar a avaliação
        return ratingRepository.save(ratingModel);
    }
}
