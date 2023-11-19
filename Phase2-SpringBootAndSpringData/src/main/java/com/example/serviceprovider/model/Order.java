package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import com.example.serviceprovider.model.enumeration.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDateTime expectedCompletionTime;

    private LocalDateTime applyTime;

    private LocalDateTime realCompletionTime;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "selected_offer_id")
    private Offer selectedOffer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();
}
