package com.goraceloty.offerservice.offer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    private Long id;
    private String name;
}
