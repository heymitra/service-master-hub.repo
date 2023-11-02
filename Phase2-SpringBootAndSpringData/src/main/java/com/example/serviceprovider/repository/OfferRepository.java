package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.order.id = :orderId")
    List<Offer> findOffersByOrder(@Param("orderId") Long orderId);
}
