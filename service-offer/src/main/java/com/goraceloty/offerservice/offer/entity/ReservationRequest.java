package com.goraceloty.offerservice.offer.entity;

import java.time.LocalDateTime;

public class ReservationRequest {
    Long offerID;
    Long hotelID;
    Long transportID;
    Long clientID;
    LocalDateTime startDate;
    Integer numOfDays;
    Integer numOfSingleRooms;
    Integer numOfDoubleRooms;
    Integer numOfTripleRooms;
    Integer numOfStudios;
    Integer numOfApartments;
    Integer numOfAdults;
    Integer numOfChildren;
}
