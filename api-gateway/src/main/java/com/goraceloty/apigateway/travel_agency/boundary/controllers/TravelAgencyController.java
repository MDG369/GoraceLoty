package com.goraceloty.apigateway.travel_agency.boundary.controllers;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.travel_agency.control.TravelAgencyClient;
import com.goraceloty.apigateway.travel_agency.entity.OfferReservation;
import com.goraceloty.apigateway.travel_agency.entity.PriceObject;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("travelagency")
public class TravelAgencyController {
    private static final int BLOCK_TIME = 30; // 30s block time
    private final AppProperties appProperties;
    private final TravelAgencyClient travelAgencyClient;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<OfferReservation> getAllOfferReservations() {
        var res = travelAgencyClient.getAllReservations().block(Duration.ofSeconds(BLOCK_TIME));
        return Arrays.asList(res);
    }

    @GetMapping("matching")
    public List<OfferReservation> getAllOfferReservations(OfferReservation offerReservation) {
        var res = travelAgencyClient.getMatchingReservations(offerReservation).block(Duration.ofSeconds(BLOCK_TIME));
        return Arrays.asList(res);
    }

    @GetMapping("price")
    public Double getPrice(PriceObject priceObject) {
        var res = travelAgencyClient.getPrice(priceObject).block(Duration.ofSeconds(BLOCK_TIME));
        return res;
    }

    @PostMapping("pay")
    public void pay(@RequestBody Long reservationID) {
        travelAgencyClient.pay(reservationID).block(Duration.ofSeconds(BLOCK_TIME));
    }
}
