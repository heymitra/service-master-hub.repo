package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "sub_services")
public class SubService extends BaseEntity<Long> {

    @NotNull(message = "SubService name cannot be null")
    private String subServiceName;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @NotNull(message = "Basic price cannot be null")
    private double basicPrice;

    @NotNull(message = "Description cannot be null")
    private String description;

    @ManyToMany(mappedBy = "subServices")
    private List<Expert> experts = new ArrayList<>();
}
