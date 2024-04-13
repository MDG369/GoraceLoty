package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    private Long id;
    private String name;
}
