package com.codecrafters.ticket_management_api.specs;

import com.codecrafters.ticket_management_api.models.OrderModel;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.UUID;

public class OrderSpecifications {

    public static Specification<OrderModel> hasOrderId(UUID orderId) {
        return (root, query, criteriaBuilder) -> orderId == null ? null
                : criteriaBuilder.equal(root.get("id"), orderId);
    }

    public static Specification<OrderModel> wasOrderedBefore(Date beforeDate) {
        return (root, query, criteriaBuilder) -> beforeDate == null ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), beforeDate);
    }

    public static Specification<OrderModel> wasOrderedAfter(Date afterDate) {
        return (root, query, criteriaBuilder) -> afterDate == null ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), afterDate);
    }

    public static Specification<OrderModel> hasOrderStatus(String orderStatus) {
        return (root, query, criteriaBuilder) -> orderStatus == null ? null
                : criteriaBuilder.like(root.get("orderStatus"), "%" + orderStatus + "%");
    }

    public static Specification<OrderModel> hasPaymentMethod(String paymentMethod) {
        return (root, query, criteriaBuilder) -> paymentMethod == null ? null
                : criteriaBuilder.like(root.get("paymentMethod"), "%" + paymentMethod + "%");
    }
}
