package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.*;
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
@Access(AccessType.FIELD)
public class Hotel {
    // HotelID, hotelName, standard, country, city, childrenAllowed, address
    @Id
    public Long hotelID;
    private String hotelName;
    private Integer standard;
    private String country;
    private String city;
    private Boolean childrenAllowed;
    private String address;

    public Long getHotelID() {
        return hotelID;
    }
}
