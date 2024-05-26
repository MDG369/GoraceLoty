package com.goraceloty.offersagaorchestrator.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessage {
    private Long offerId;
    private ErrorType errorType;
}
