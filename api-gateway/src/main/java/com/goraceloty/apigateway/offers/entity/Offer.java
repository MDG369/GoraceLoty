package com.goraceloty.apigateway.offers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    private Long id;
    private Long transportID;
    private Long hotelID;
    private String cityArrival;
    private String cityDeparture;
    private String dateStart;
    private String dateEnd;
    private Boolean available;
}

