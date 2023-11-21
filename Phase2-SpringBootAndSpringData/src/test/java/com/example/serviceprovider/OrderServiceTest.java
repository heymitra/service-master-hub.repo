//package com.example.serviceprovider;
//
//import com.example.serviceprovider.model.Expert;
//import com.example.serviceprovider.model.Offer;
//import com.example.serviceprovider.model.Order;
//import com.example.serviceprovider.model.SubService;
//import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
//import com.example.serviceprovider.repository.OrderRepository;
//import com.example.serviceprovider.service.OfferService;
//import com.example.serviceprovider.service.impl.OrderServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.mockito.Mockito.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//@Transactional
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private OfferService offerService;
//
//    private OrderServiceImpl orderService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        orderService = new OrderServiceImpl(orderRepository);
//        orderService.setOfferService(offerService);
//    }
//
//    @Test
//    public void testSaveOrderWithValidData() {
//        Order order = new Order();
//        SubService subService = new SubService();
//        subService.setBasePrice(100.0);
//        order.setSubService(subService);
//        order.setProposedPrice(120.0);
//        order.setCompletionDateTime(LocalDateTime.now().plusDays(1));
//        order.setAddress("Sample Address");
//
//        when(orderRepository.save(order)).thenReturn(order);
//
//        Order savedOrder = orderService.saveOrder(order);
//
//        Mockito.verify(orderRepository).save(order);
//        assert savedOrder != null;
//        assert savedOrder.equals(order);
//    }
//
//    @Test
//    public void testSaveOrderWithInvalidProposedPrice() {
//        Order order = new Order();
//        SubService subService = new SubService();
//        subService.setBasePrice(100.0);
//        order.setSubService(subService);
//        order.setProposedPrice(80.0);
//        order.setCompletionDateTime(LocalDateTime.now().plusDays(1));
//        order.setAddress("Sample Address");
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(order));
//
//        Mockito.verifyNoInteractions(orderRepository);
//    }
//
//    @Test
//    public void testSaveOrderWithPastCompletionDateTime() {
//        Order order = new Order();
//        SubService subService = new SubService();
//        subService.setBasePrice(100.0);
//        order.setSubService(subService);
//        order.setProposedPrice(120.0);
//        order.setCompletionDateTime(LocalDateTime.now().minusDays(1));
//        order.setAddress("Sample Address");
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(order));
//
//        Mockito.verifyNoInteractions(orderRepository);
//    }
//
//    @Test
//    public void testGetOrdersForExpert() {
//        // Create a sample expert and related sub-services
//        Expert expert = new Expert();
//        SubService subService1 = new SubService();
//        SubService subService2 = new SubService();
//        expert.setSubServices(Arrays.asList(subService1, subService2));
//
//        // Create sample orders
//        Order order1 = new Order();
//        order1.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
//        order1.setSubService(subService1);
//        Order order2 = new Order();
//        order2.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION);
//        order2.setSubService(subService2);
//        Order order3 = new Order();
//        order3.setStatus(OrderStatusEnum.COMPLETED);
//        order3.setSubService(subService1);
//
//        List<Order> allOrders = Arrays.asList(order1, order2, order3);
//
//        when(orderRepository.findOrdersByStatusAndSubServiceIn(
//                Arrays.asList(OrderStatusEnum.WAITING_FOR_OFFERS, OrderStatusEnum.WAITING_FOR_EXPERT_SELECTION),
//                Arrays.asList(subService1, subService2)))
//                .thenReturn(Arrays.asList(order1, order2));
//
//        List<Order> expertOrders = orderService.getOrdersForExpert(expert);
//
//        assertEquals(2, expertOrders.size());
//        assertTrue(expertOrders.contains(order1));
//        assertTrue(expertOrders.contains(order2));
//        assertFalse(expertOrders.contains(order3));
//    }
//
//    @Test
//    public void testSelectOfferAndChangeOrderStatus() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.WAITING_FOR_OFFERS);
//
//        Offer offer = new Offer();
//        offer.setOrder(order);
//
//        when(offerService.save(offer)).thenReturn(offer);
//
//        orderService.selectOfferAndChangeOrderStatus(order, offer);
//
//        assertEquals(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME, order.getStatus());
//
//        verify(orderRepository, times(1)).save(order);
//        verify(offerService, times(1)).save(offer);
//    }
//
//    @Test
//    public void testSelectOfferAndChangeOrderStatusInvalidOffer() {
//        Order order = new Order();
//
//        Offer offer = new Offer();
//
//        assertThrows(NullPointerException.class, () -> orderService.selectOfferAndChangeOrderStatus(order, offer));
//
//        verify(orderRepository, never()).save(order);
//        verify(offerService, never()).save(offer);
//    }
//
//    @Test
//    public void testStartOrderValid() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME);
//
//        Offer offer = new Offer();
//        offer.setOfferedStartTime(LocalDateTime.now().minusMinutes(30)); // Past time
//
//        order.setSelectedOffer(offer);
//
//        when(orderRepository.save(order)).thenReturn(order);
//
//        assertDoesNotThrow(() -> orderService.startOrder(order));
//
//        assertEquals(OrderStatusEnum.STARTED, order.getStatus());
//
//        verify(orderRepository, times(1)).save(order);
//    }
//
//
//    @Test
//    public void testStartOrderInvalidStatus() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.STARTED);
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.startOrder(order));
//
//        verify(orderRepository, never()).save(order);
//    }
//
//    @Test
//    public void testStartOrderNoSelectedOffer() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME);
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.startOrder(order));
//
//        verify(orderRepository, never()).save(order);
//    }
//
//    @Test
//    public void testStartOrderBeforeOfferedStartTime() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME);
//
//        Offer offer = new Offer();
//        offer.setOfferedStartTime(LocalDateTime.now().plusMinutes(30)); // Future time
//
//        order.setSelectedOffer(offer);
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.startOrder(order));
//
//        verify(orderRepository, never()).save(order);
//    }
//
//    @Test
//    public void testCompleteOrderValid() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.STARTED);
//
//        when(orderRepository.save(order)).thenReturn(order);
//
//        assertDoesNotThrow(() -> orderService.completeOrder(order));
//
//        assertEquals(OrderStatusEnum.COMPLETED, order.getStatus());
//
//        verify(orderRepository, times(1)).save(order);
//    }
//
//    @Test
//    public void testCompleteOrderInvalidStatus() {
//        Order order = new Order();
//        order.setStatus(OrderStatusEnum.WAITING_FOR_EXPERT_TO_COME); // Invalid status
//
//        assertThrows(IllegalArgumentException.class, () -> orderService.completeOrder(order));
//
//        verify(orderRepository, never()).save(order);
//    }
//}
