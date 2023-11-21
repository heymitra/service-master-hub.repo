package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.*;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PreAuthorize("hasRole('EXPERT')")
    @PostMapping ("/add")
    public ResponseEntity<OfferResponseDto> add (@RequestBody @Valid OfferRequestDto offerRequestDto,
                                                 @RequestParam Long orderId) {
        Offer offer = modelMapper.map(offerRequestDto, Offer.class);
        Offer savedOffer = offerService.save(offer, orderId);
        OfferResponseDto offerMapper = new OfferResponseDto();
        OfferResponseDto offerResponseDTO = offerMapper.modelToDto(offer);
        return new ResponseEntity<>(offerResponseDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping ("/get-offers")
    public List<OfferResponseDto> viewOffersOfOrder(@RequestParam Long orderId, @RequestParam String sortBy) {
        List<Offer> offers = offerService.viewOffers(orderId, sortBy);
        List<OfferResponseDto> offersDto = new ArrayList<>();
        OfferResponseDto offerMapper = new OfferResponseDto();
        for (Offer offer : offers) {
            offersDto.add(offerMapper.modelToDto(offer));
        }
        return offersDto;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping ("/offer-selection")
    public void selectOffer (@RequestParam Long orderId, @RequestParam Long offerId) {
        offerService.selectOffer(orderId, offerId);
    }

    @PreAuthorize("hasRole('EXPERT')")
    @GetMapping("/my-orders")
    public List<OfferReportDto> getSelectedOffersByExpertAndOrderStatus(@RequestParam(required = false) String status) {
        OrderStatusEnum orderStatus = null;
        if (status != null) {
            try {
                orderStatus = OrderStatusEnum.valueOf(status);
            } catch (IllegalArgumentException e) {
                logger.error("Error: Invalid OrderStatusEnum value provided: {}", status);
            }
        }

        List<Offer> selectedOffers = offerService.getSelectedOffersByExpertAndOrderStatus(orderStatus);

        return selectedOffers.stream()
                .map(offer -> {
                    OfferReportDto dto = modelMapper.map(offer, OfferReportDto.class);
                    dto.setExpert(modelMapper.map(offer.getExpert(), ExpertResponseDto.class));
                    dto.setOrder(modelMapper.map(offer.getOrder(), OrderResponseDto.class));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
