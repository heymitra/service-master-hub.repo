package com.example.serviceprovider;

import com.example.serviceprovider.model.Expert;
import com.example.serviceprovider.model.Offer;
import com.example.serviceprovider.model.Order;
import com.example.serviceprovider.model.SubService;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import com.example.serviceprovider.repository.OfferRepository;
import com.example.serviceprovider.service.OfferService;
import com.example.serviceprovider.service.impl.OfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.lang.IllegalArgumentException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferService offerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        offerService = new OfferServiceImpl(offerRepository);
    }

    @Test
    public void testSaveOfferValidData() {
        // Create a sample offer
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
        SubService subService = new SubService();
        subService.setBasicPrice(100.0);
        order.setSubService(subService);
        offer.setOrder(order);
        offer.setOfferedPrice(120.0);
        offer.setOfferedStartTime(LocalDateTime.now().plusHours(1));

        when(offerRepository.save(offer)).thenReturn(offer);

        Offer savedOffer = offerService.saveOffer(offer);

        assert (savedOffer).equals(offer);
    }

    @Test
    public void testSaveOfferInvalidOrderStatus() {
        // Create a sample offer
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatusEnum.COMPLETED); // Invalid status
        offer.setOrder(order);

        assertThrows(IllegalArgumentException.class, () -> offerService.saveOffer(offer));
    }

    @Test
    public void testSaveOfferInvalidProposedPrice() {
        // Create a sample offer
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
        SubService subService = new SubService();
        subService.setBasicPrice(100.0);
        order.setSubService(subService);
        offer.setOrder(order);
        offer.setOfferedPrice(80.0);
        offer.setOfferedStartTime(LocalDateTime.now().plusHours(1));

        assertThrows(IllegalArgumentException.class, () -> offerService.saveOffer(offer));
    }

    @Test
    public void testSaveOfferInvalidStartTime() {
        Offer offer = new Offer();
        Order order = new Order();
        order.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
        SubService subService = new SubService();
        subService.setBasicPrice(100.0);
        order.setSubService(subService);
        offer.setOrder(order);
        offer.setOfferedPrice(120.0);
        offer.setOfferedStartTime(LocalDateTime.now().minusHours(1));

        assertThrows(IllegalArgumentException.class, () -> offerService.saveOffer(offer));
    }

    @Test
    public void testViewCustomerOrderOffersWhenNoOffersExist() {
        Order order = new Order();

        when(offerRepository.findOffersByOrder(order)).thenReturn(Collections.emptyList());

        List<Offer> result = offerService.viewCustomerOrderOffers(order, "price");

        verify(offerRepository, times(1)).findOffersByOrder(order);
        assertEquals(0, result.size());
    }

    @Test
    public void testViewCustomerOrderOffersSortedByPrice() {
        Order order = new Order();

        List<Offer> offers = Arrays.asList(
                createOffer(1.0),
                createOffer(2.0),
                createOffer(0.5)
        );

        when(offerRepository.findOffersByOrder(order)).thenReturn(offers);

        List<Offer> result = offerService.viewCustomerOrderOffers(order, "price");

        verify(offerRepository, times(1)).findOffersByOrder(order);
        assertEquals(3, result.size());
        assertEquals(0.5, result.get(0).getOfferedPrice());
        assertEquals(1.0, result.get(1).getOfferedPrice());
        assertEquals(2.0, result.get(2).getOfferedPrice());
    }

    @Test
    public void testViewCustomerOrderOffersSortedByScore() {
        Order order = new Order();

        List<Offer> offers = Arrays.asList(
                createOfferWithExpert(createExpert(3)),
                createOfferWithExpert(createExpert(2)),
                createOfferWithExpert(createExpert(5))
        );

        when(offerRepository.findOffersByOrder(order)).thenReturn(offers);

        List<Offer> result = offerService.viewCustomerOrderOffers(order, "score");

        verify(offerRepository, times(1)).findOffersByOrder(order);
        assertEquals(3, result.size());
        assertEquals(5, result.get(0).getExpert().getScore());
        assertEquals(3, result.get(1).getExpert().getScore());
        assertEquals(2, result.get(2).getExpert().getScore());
    }

//    @Test
//    public void testInvalidSortingCriteria() {
//        Order order = new Order();
//        SubService subService = new SubService();
//        subService.setBasicPrice(100.0);
//        order.setSubService(subService);
//        order.setProposedPrice(120.0);
//        order.setCompletionDateTime(LocalDateTime.now().plusDays(1));
//        order.setAddress("Sample Address");
//
//        String invalidSortBy = "invalidSort";
//
//        when(offerRepository.findOffersByOrder(order)).thenReturn(Collections.emptyList());
//
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> offerService.viewCustomerOrderOffers(order, invalidSortBy)
//        );
//
//        assertTrue(exception.getMessage().contains("Invalid sorting criteria"));
//    }

    private Offer createOffer(double price) {
        Offer offer = new Offer();
        offer.setOfferedPrice(price);
        return offer;
    }

    private Expert createExpert(int score) {
        Expert expert = new Expert();
        expert.setScore(score);
        return expert;
    }

    private Offer createOfferWithExpert(Expert expert) {
        Offer offer = new Offer();
        offer.setExpert(expert);
        return offer;
    }
}

