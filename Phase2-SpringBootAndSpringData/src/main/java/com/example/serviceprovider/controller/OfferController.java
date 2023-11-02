package com.example.serviceprovider.controller;

import com.example.serviceprovider.dto.OfferRequestDto;
import com.example.serviceprovider.dto.OfferResponseDTO;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.service.OfferService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping ("/add")
    public ResponseEntity<OfferResponseDTO> add (@RequestBody @Valid OfferRequestDto offerRequestDto,
                                                 @RequestParam Long expertId,
                                                 @RequestParam Long orderId) {
        Offer offer = modelMapper.map(offerRequestDto, Offer.class);
        Offer savedOffer = offerService.save(offer, expertId, orderId);
        OfferMapper offerMapper = new OfferMapper();
        OfferResponseDTO offerResponseDTO = offerMapper.modelToDto(offer);
        return new ResponseEntity<>(offerResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping ("/get-offers")
    public List<OfferResponseDTO> viewOffersOfOrder(@RequestParam Long orderId, @RequestParam String sortBy) {
        List<Offer> offers = offerService.viewOffers(orderId, sortBy);
        List<OfferResponseDTO> offersDto = new ArrayList<>();
        OfferMapper offerMapper = new OfferMapper();
        for (Offer offer : offers) {
            offersDto.add(offerMapper.modelToDto(offer));
        }
        return offersDto;
    }

    @PutMapping ("/offer-selection")
    public void selectOffer (@RequestParam Long orderId, @RequestParam Long offerId) {
        offerService.selectOffer(orderId, offerId);
    }
}

class OfferMapper {
    public OfferResponseDTO modelToDto(Offer offer) {
        return new OfferResponseDTO(offer.getId(),
                offer.getOrder().getId(),
                offer.getExpert().getId());
    }
}
