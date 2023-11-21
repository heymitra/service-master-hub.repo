package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.*;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.email = :email AND (:status IS NULL OR o.status = :status)")
    List<Order> findOrdersByCustomerAndStatus(@Param("email") String email, @Param("status") OrderStatusEnum status);


    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findOrdersByCustomerId(@Param("customerId") long customerId);


    @Query("SELECT o FROM Order o WHERE o.selectedOffer.expert.id = :expertId AND o.selectedOffer.isSelected = true")
    List<Order> findOrdersByExpertIdAndSelectedOfferIsTrue(@Param("expertId") long expertId);


    @Query("SELECT o FROM Order o " +
            "WHERE (CAST(:startTime AS date) IS NULL OR o.applyTime >= :startTime) " +
            "AND (CAST(:endTime AS date) IS NULL OR o.applyTime <= :endTime) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:subServiceId IS NULL OR o.subService.id = :subServiceId) " +
            "AND (:serviceId IS NULL OR o.subService.service.id = :serviceId)")
    List<Order> findOrdersByCriteria(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") OrderStatusEnum status,
            @Param("subServiceId") Long subServiceId,
            @Param("serviceId") Long serviceId
    );


    @Query("SELECT o FROM Order o WHERE o.status IN :statuses AND o.subService IN :subServices")
    List<Order> findOrdersByStatusAndSubServiceIn(@Param("statuses") List<OrderStatusEnum> statuses,
                                                  @Param("subServices") List<SubService> subServices);


    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    default Page<Order> searchAndFilterOrders(String customerEmail, String status, Pageable pageable) {
        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (customerEmail != null && !customerEmail.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.join("customer").get("email"), customerEmail));
            }

            if (status != null && !status.isEmpty()) {
                OrderStatusEnum statusEnum = OrderStatusEnum.valueOf(status);
                predicates.add(criteriaBuilder.equal(root.get("status"), statusEnum));
            }

            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
