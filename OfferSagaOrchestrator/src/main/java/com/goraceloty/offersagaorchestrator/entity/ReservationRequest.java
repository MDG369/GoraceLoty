package com.goraceloty.offersagaorchestrator.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationRequest {
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
