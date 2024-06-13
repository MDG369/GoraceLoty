package com.goraceloty.offerservice.offer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeMessage {
    private String content;
    private Long hotelId;
    private Long transportId;
    private Long offerId;
    private Long clientId;

    public ChangeMessage() {

    }
}