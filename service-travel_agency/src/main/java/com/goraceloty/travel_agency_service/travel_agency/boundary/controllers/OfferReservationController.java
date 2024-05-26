package com.goraceloty.travel_agency_service.travel_agency.boundary.controllers;

import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import com.goraceloty.travel_agency_service.travel_agency.control.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/OfferReservation")
@RequiredArgsConstructor
public class OfferReservationController {
    private final TravelAgencyService travelAgencyService;

    @GetMapping("/connections")
    ResponseEntity<String> getEx() {
        return ResponseEntity.ok().body("\"Connection to hotel service works!\"");
    }
    @GetMapping("/matching")
    public List<OfferReservation> getOfferReservationByExample(OfferReservation offerReservation) {
        return travelAgencyService.getOfferReservationByExample(offerReservation);
    }

    @GetMapping("/price")
    public double getAdjustedPrice(@RequestParam Long transportId) {
        return travelAgencyService.calculatePriceBasedOnSeats(transportId);
    }
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
