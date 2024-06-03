package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
