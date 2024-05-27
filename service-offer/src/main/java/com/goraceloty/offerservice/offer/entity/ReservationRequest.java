package com.goraceloty.offerservice.offer.entity;

import java.time.LocalDate;

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
