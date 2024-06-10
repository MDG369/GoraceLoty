package com.goraceloty.travel_agency_service.travel_agency.boundary.controllers;

import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import com.goraceloty.travel_agency_service.travel_agency.control.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.goraceloty.apigateway.travel_agency.entity.PriceObject;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class OfferReservationController {
    private final TravelAgencyService travelAgencyService;

    @GetMapping("/matching")
    public List<OfferReservation> getOfferReservationByExample(OfferReservation offerReservation) {
        return travelAgencyService.getOfferReservationByExample(offerReservation);
    }

    @GetMapping("/price")
    public double getAdjustedPrice(
            @RequestBody PriceObject priceObject) {
        return travelAgencyService.calculatePrice(priceObject);
    }

    @PostMapping("/pay")
    public void pay(@RequestBody Long reservationId) {
        System.out.println("pay request received");
        travelAgencyService.pay(reservationId);
    }
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
