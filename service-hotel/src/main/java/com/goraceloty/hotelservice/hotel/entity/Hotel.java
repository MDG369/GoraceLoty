package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotel {
    // HotelID, hotelName, standard, country, city, childrenAllowed, address
    @Id
    private Long id;
    private String name;
    private String country;
    private String city;
    private Integer numOfSingleRooms;
    private Integer numOfDoubleRooms;
    private Integer numOfTripleRooms;
    private Integer stars;
}
