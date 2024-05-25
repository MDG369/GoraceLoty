package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hotel {
    // HotelID, hotelName, standard, country, city, childrenAllowed, address
    @Id
    private Long hotelID;
    private String hotelName;
    private Integer standard;
    private String country;
    private String city;
    private Boolean childrenAllowed;
    private String address;
}
