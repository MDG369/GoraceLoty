package com.goraceloty.hotelservice.saga.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequest {
    UUID id = null;
    Long offerID;
    Long hotelID;
    Long transportID;
    List<String> dates;
    Integer numOfSingleRooms;
    Integer numOfDoubleRooms;
    Integer numOfTripleRooms;
    Integer numOfStudios;
    Integer numOfApartments;
    Integer numOfAdults;
    Integer numOfChildren;
}
