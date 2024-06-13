package com.goraceloty.apigateway.offers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferChange {
    private Long changeID;
    private Long offerID;
    private String committedChange;
    private String commitType;
    private LocalDateTime createdAt;
}

