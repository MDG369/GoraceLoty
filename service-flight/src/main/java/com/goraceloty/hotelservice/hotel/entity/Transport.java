package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transports")
public class Transport {
    @Id
    private Long id;
    private String name;
    private Integer numTotalSeats;
    private Integer numAvailableSeats;
    private Integer numBasePrice;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime dateDeparture;
    private LocalDateTime dateArrival;
}