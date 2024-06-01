package com.goraceloty.offersagaorchestrator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ErrorMessage {
    private ReservationRequest reservationRequest;
    private ErrorType errorType;
}
