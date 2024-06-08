package com.goraceloty.apigateway.saga.entity;

import lombok.Data;

@Data
public class ChangeMessage {
    private String content;
    private Long hotelId;
    private Long flightId;
    private Long offerId;
}
