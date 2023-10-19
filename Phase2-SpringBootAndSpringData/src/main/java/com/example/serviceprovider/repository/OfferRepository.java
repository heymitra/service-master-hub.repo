package com.example.serviceprovider.repository;

import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.order = :order")
    List<Offer> findOffersByOrder(@Param("order") Order order);
}
