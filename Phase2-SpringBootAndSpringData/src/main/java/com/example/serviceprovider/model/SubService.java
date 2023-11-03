package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "sub_services")
public class SubService extends BaseEntity<Long> {
    @Column(unique = true)
    private String subServiceName;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private double basePrice;

    private String description;

    @ManyToMany(mappedBy = "subServices")
    private List<Expert> experts = new ArrayList<>();
}
