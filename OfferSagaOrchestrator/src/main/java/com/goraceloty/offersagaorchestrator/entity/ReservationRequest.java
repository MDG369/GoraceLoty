package com.goraceloty.offersagaorchestrator.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@Setter
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
