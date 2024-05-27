package com.goraceloty.travel_agency_service.travel_agency.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "offerReservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferReservation {
    @Id
    private Long reservationID;
    private LocalDateTime dateStart;
    private Integer numAdult;
    private Integer numChildren;
    private Integer numOfDays;
    private Timestamp reservationTime;
    private Double adjustedPrice;
    private Long offerID;
    private Long hotelID;
    private Long transportID;
}
