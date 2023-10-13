package entity;

import base.entity.BaseEntity;
import entity.enumeration.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "subservice_id")
    private SubService subService;

    @Min(value = 0, message = "Proposed price cannot be negative")
    private double proposedPrice;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String workDescription;

    @FutureOrPresent(message = "Completion date and time cannot be in the past")
    private LocalDateTime completionDateTime;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
}

