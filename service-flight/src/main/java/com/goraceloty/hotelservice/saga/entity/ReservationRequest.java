package com.goraceloty.hotelservice.saga.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
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
