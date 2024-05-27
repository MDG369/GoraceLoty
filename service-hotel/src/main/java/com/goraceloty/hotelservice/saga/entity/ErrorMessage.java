package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
