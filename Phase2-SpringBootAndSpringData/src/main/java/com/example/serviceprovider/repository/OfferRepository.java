package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
    @Query("SELECT o FROM Offer o WHERE o.order.id = :orderId")
    List<Offer> findOffersByOrder(@Param("orderId") Long orderId);


    @Query("SELECT o FROM Offer o WHERE o.expert.email = :email AND o.isSelected = true AND " +
            "(:orderStatus IS NULL OR o.order.status = :orderStatus)")
    List<Offer> findSelectedOffersByExpertAndOrderStatus(
            @Param("email") String email,
            @Param("orderStatus") OrderStatusEnum orderStatus
    );
}
