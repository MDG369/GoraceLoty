package com.goraceloty.apigateway.travel_agency.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferReservation {
    private Long reservationID;
    private LocalDate startDate;
    private Integer numAdult;
    private Integer numChildren;
    private Integer numOfDays;
    private Timestamp reservationTime;
    private Double adjustedPrice;
    private Long offerID;
    private Long hotelID;
    private Long transportID;
    private Boolean isPaid;
    private Long clientID;
}

