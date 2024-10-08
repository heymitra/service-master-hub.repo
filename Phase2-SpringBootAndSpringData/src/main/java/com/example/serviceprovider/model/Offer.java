package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity<Long> {

    private LocalDateTime offerDateTime;

    private double offeredPrice;

    private LocalDateTime offeredStartTime;

    private int offeredDurationInHours;

    private boolean isSelected;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
