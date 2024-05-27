package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
