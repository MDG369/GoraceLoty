package com.goraceloty.travel_agency_service.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    UUID reservationRequestID;
    Long offerID;
    Long hotelID;
    Long transportID;
    Long clientID;
    LocalDate startDate;
    Integer numOfDays;
    Integer numOfSingleRooms;
    Integer numOfDoubleRooms;
    Integer numOfTripleRooms;
    Integer numOfStudios;
    Integer numOfApartments;
    Integer numOfAdults;
    Integer numOfChildren;
}
