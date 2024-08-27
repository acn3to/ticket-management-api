package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.dtos.RatingRecordDto;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.models.RatingModel;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.repositories.RatingRepository;
import com.codecrafters.ticket_management_api.specifications.RatingSpecification;
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

    public RatingModel publishRating(RatingRecordDto ratingRecordDto) {
        // Validar se o ratingModel contém todos os campos obrigatórios
//        if (ratingModel.getRating() == null || ratingModel.getEvent() == null || ratingModel.getUserId() == null) {
//            throw new IllegalArgumentException("Rating, Event, and UserId are required");
//        }
        // Salvar e retornar a avaliação
        EventModel eventModel = eventRepository.findById(ratingRecordDto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));


        RatingModel ratingModel = new RatingModel();
        ratingModel.setRating(ratingRecordDto.getRating());
        ratingModel.setEvent(eventModel);
        ratingModel.setReview(ratingRecordDto.getReview());

        return ratingRepository.save(ratingModel);
    }
}
