package com.goraceloty.travel_agency_service.saga.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
