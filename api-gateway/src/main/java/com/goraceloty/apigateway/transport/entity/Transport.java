package com.goraceloty.apigateway.transport.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transport {
    private Long transportID;
    private String typeOfTransport;
    private Integer numTotalSeats;
    private Integer numAvailableSeats;
    private Integer numBasePrice;
    private String cityDeparture;
    private String cityArrival;
    private LocalDateTime dateDeparture;
    private LocalDateTime dateArrival;

}