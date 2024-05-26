package com.goraceloty.offersagaorchestrator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SagaHotelBookingMessage {
    private Long reservationId;
    private Long hotelId;
    private List<String> dates;
    private Integer numOfBookedSingleRooms;
    private Integer numOfBookedDoubleRooms;
    private Integer numOfBookedTripleRooms;
    private Integer numOfBookedStudios;
    private Integer numOfBookedApartments;
}
