package com.goraceloty.travel_agency_service.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
