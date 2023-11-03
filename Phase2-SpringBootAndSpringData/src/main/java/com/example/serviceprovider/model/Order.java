package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "subservice_id")
    private SubService subService;

    private double proposedPrice;

    @Basic(fetch = FetchType.LAZY)
    private String workDescription;

    private LocalDateTime completionDateTime;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "selected_offer_id")
    private Offer selectedOffer;
}
