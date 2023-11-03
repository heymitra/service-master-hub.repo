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
@Table(name = "services")
public class Service extends BaseEntity<Long> {
    @Column(unique = true)
    private String serviceName;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<SubService> subServices = new ArrayList<>();
}

