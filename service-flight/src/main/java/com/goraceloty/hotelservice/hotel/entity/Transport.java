package com.goraceloty.hotelservice.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transports")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transport {
    @Id
    private Long transportID;
    private String typeOfTransport;
    private Integer numTotalSeats;
    private Integer numAvailableSeats;
    private Integer numBasePrice;
    private String cityDeparture;
    private String cityArrival;
    private LocalDateTime dateDeparture;
    private LocalDateTime dateArrival;

}