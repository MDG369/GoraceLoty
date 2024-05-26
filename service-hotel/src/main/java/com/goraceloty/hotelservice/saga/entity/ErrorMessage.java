package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessage {
    private Long offerId;
    private ErrorType errorType;
}
