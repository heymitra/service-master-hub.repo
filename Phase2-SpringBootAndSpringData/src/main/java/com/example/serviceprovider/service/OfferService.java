package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;

import java.util.List;

public interface OfferService {
    Offer save(Offer offer, Long orderId);
    Offer update(Offer offer);
    List<Offer> viewOffers(Long orderId, String sortBy);
    void selectOffer(Long orderId, Long selectedOfferId);
    List<Offer> getSelectedOffersByExpertAndOrderStatus(OrderStatusEnum status);
}
