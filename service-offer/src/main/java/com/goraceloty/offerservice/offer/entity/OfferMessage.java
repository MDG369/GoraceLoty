package com.goraceloty.offerservice.offer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferMessage implements Serializable {
    private long ID;
    private int value;
    private String logMessage;
    private String messageType;
    private String exchange;
}