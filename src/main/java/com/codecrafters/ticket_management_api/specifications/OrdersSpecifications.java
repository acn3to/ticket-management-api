package com.codecrafters.ticket_management_api.specifications;

import com.codecrafters.ticket_management_api.models.OrdersModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersSpecifications {

    public Specification<OrdersModel> findOrderBy(
            Long userId,
            Long orderId,
            Date beforeDate,
            Date afterDate,
            String orderStatus,
            String paymentMethods
    ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId!= null){
                predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
            }
            if (orderId!= null){
                predicates.add(criteriaBuilder.equal(root.get("id"),orderId));
            }
            if (beforeDate!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"),beforeDate));
            }
            if (afterDate!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"),afterDate));
            }
            if (beforeDate!=null && afterDate!= null){
                predicates.add(criteriaBuilder.between(root.get("orderDate"),beforeDate,afterDate));
            }
            if (!orderStatus.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("orderStatus"),orderStatus));
            }
            if (!paymentMethods.isEmpty()){
                predicates.add(criteriaBuilder.like(root.get("paymentMethods"),paymentMethods));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}


