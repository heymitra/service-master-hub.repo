package com.example.serviceprovider.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;

    public BaseEntity(ID id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}