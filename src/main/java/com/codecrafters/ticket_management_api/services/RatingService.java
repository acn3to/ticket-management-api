package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.dto.RatingDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.models.RatingModel;
import com.codecrafters.ticket_management_api.models.UserModel;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.repositories.RatingRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import com.codecrafters.ticket_management_api.specs.RatingSpecifications;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
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

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public List<RatingModel> getAllRatings(
            EventModel event,
            UUID user,
            BigDecimal minRating,
            BigDecimal maxRating,
            String review,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore) {
        try {
            Specification<RatingModel> spec = Specification
                    .where(RatingSpecifications.hasEvent(event))
                    .and(RatingSpecifications.hasRatingBetween(minRating, maxRating))
                    .and(RatingSpecifications.hasReviewContaining(review))
                    .and(RatingSpecifications.isCreatedAfter(createdAfter))
                    .and(RatingSpecifications.isCreatedBefore(createdBefore));

            return ratingRepository.findAll(spec);
        } catch (Exception e) {
            throw new CustomException("Error retrieving ratings: " + e.getMessage());
        }
    }

    public RatingModel createRating(RatingDTO data) {
        try {
            EventModel event = eventRepository.findById(data.eventId())
                    .orElseThrow(() -> new CustomException("Event not found with ID: " + data.eventId()));

            UserModel user = userRepository.findById(data.userId())
                    .orElseThrow(() -> new CustomException("User not found with ID: " + data.userId()));

            RatingModel newRating = RatingModel.builder()
                    .rating(data.rating())
                    .event(event)
                    .user(user)
                    .review(data.review())
                    .build();

            return ratingRepository.save(newRating);
        } catch (Exception e) {
            throw new CustomException("Error creating rating: " + e.getMessage());
        }
    }

    public void deleteRating(UUID ratingId) {
        try {
            if (!ratingRepository.existsById(ratingId)) {
                throw new CustomException("Rating not found with ID: " + ratingId);
            }
            ratingRepository.deleteById(ratingId);
        } catch (Exception e) {
            throw new CustomException("Error deleting rating: " + e.getMessage());
        }
    }
}
