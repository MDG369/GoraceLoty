package com.goraceloty.apigateway.travel_agency.entity;

import ch.qos.logback.core.net.server.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceObject {
    int numAdults;
    int numChildren;
    long transportId;
    long hotelId;
    int duration;
    int numOfSingleRooms;
    int numOfDoubleRooms;
    int numOfTripleRooms;
    int numOfStudios;
    int numOfApartments;
}

