package com.goraceloty.travel_agency_service.travel_agency.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HotelDataDTO {
    private Integer numTotalSeats;
    private int numAvailableSeats;
}
