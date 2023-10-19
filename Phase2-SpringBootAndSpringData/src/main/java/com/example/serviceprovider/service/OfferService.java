package com.example.serviceprovider.service;

import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;

import java.util.List;

public interface OfferService {
    Offer saveOffer(Offer offer);
    Offer save(Offer offer);
    List<Offer> viewCustomerOrderOffers(Order order, String sortBy);
}
