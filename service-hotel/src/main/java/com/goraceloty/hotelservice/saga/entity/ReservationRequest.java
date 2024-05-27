package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ReservationRequest {
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
