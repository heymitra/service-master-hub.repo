package com.example.serviceprovider.model;

import com.example.serviceprovider.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Service name is required")
    private String serviceName;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<SubService> subServices = new ArrayList<>();
}

