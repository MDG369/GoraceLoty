package com.goraceloty.apigateway.offers.boundary.controllers;

import com.goraceloty.apigateway.offers.control.OfferClient;
import com.goraceloty.apigateway.offers.entity.Offer;
import com.goraceloty.apigateway.saga.entity.ReservationRequest;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("offers")
public class OfferController {

    private static final int BLOCK_TIME = 30; // 30s block time
    private final OfferClient offerClient;

    @GetMapping("matching")
    public List<Offer> getOfferByExample(Offer offer) {
        var res = offerClient.getOffersByExample(offer).block(Duration.ofSeconds(BLOCK_TIME));
        return res != null ? Arrays.stream(res).collect(Collectors.toList()) : null;
    }

    @PostMapping("book")
    public Long bookOffer(@RequestBody ReservationRequest reservationRequest) {
        var res = offerClient.tryBookingOffer(reservationRequest).block(Duration.ofSeconds(BLOCK_TIME));
        return res;
    }
    //@GetMapping("/{id}")
    //public Offer getOfferById(@PathVariable("id") Long id) {
    //    var res = offerClient.getOffersById(id).block(Duration.ofSeconds(BLOCK_TIME));
    //    return res;
    //}

}
