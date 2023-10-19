package com.example.serviceprovider.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Customer")
public class Customer extends User {
}

