package com.goraceloty.travel_agency_service.travel_agency.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeMessage {
    private String content;
    private Long hotelId;
    private Long transportId;
    private Long offerId;
}

