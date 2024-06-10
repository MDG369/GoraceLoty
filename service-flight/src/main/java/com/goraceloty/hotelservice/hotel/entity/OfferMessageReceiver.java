package com.goraceloty.hotelservice.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferMessageReceiver implements Serializable {
    private long ID;
    private int value;
    private String logMessage;
    private String messageType;
    private String exchange;
}
