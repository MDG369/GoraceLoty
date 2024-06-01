package com.goraceloty.offersagaorchestrator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class ReservationRequest {
    UUID reservationRequestID = UUID.randomUUID();
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
