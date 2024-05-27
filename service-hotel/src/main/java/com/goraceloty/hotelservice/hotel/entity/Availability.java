package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "availability")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Availability {
    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    private Long availabilityID;
    private Long hotelID;
    private LocalDate date;
    private Integer numOfAvSingleRooms;
    private Integer numOfAvDoubleRooms;
    private Integer numOfAvTripleRooms;
    private Integer numOfAvStudios;
    private Integer numOfAvApartments;
}