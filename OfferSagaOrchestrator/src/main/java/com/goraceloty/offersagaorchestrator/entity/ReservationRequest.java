package com.goraceloty.offersagaorchestrator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    Offer offer;
    Integer numOfSingleRooms;
    Integer numOfDoubleRooms;
    Integer numOfTripleRooms;
    Integer numOfStudios;
    Integer numOfApartments;
    Integer numOfAdults;
    Integer numOfChildren;
}
