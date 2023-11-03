package com.example.serviceprovider.model;

import com.example.serviceprovider.model.enumeration.ExpertStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@DiscriminatorValue("Expert")
public class Expert extends User {

//    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] personalPhoto;

    private int rate;

    @Enumerated(EnumType.STRING)
    private ExpertStatusEnum expertStatus;

    @ManyToMany
    @JoinTable(
            name = "expert_subservices",
            joinColumns = @JoinColumn(name = "expert_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id"))
    private List<SubService> subServices = new ArrayList<>();
}
