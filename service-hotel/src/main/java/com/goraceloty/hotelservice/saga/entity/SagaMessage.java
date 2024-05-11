package com.goraceloty.hotelservice.saga.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SagaMessage {

    public SagaMessage(String status) {
        this.status = status;
    }

    private String status;
    private Long orderId;
}

