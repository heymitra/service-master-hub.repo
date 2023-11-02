package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Offer;

import java.util.List;

public interface OfferService {
    Offer save(Offer offer, Long expertId, Long offerId);
    Offer update(Offer offer);
    List<Offer> viewOffers(Long orderId, String sortBy);
    void selectOffer(Long orderId, Long selectedOfferId);
}
