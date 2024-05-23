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
