package com.goraceloty.travel_agency_service.travel_agency.boundary.controllers;

import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import com.goraceloty.travel_agency_service.travel_agency.control.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
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
    public double getAdjustedPrice(
            @RequestParam int numAdults,
            @RequestParam int numChildren,
            @RequestParam long transportId,
            @RequestParam long hotelId,
            @RequestParam int duration,
            @RequestParam int numOfSingleRooms,
            @RequestParam int numOfDoubleRooms,
            @RequestParam int numOfTripleRooms,
            @RequestParam int numOfStudios,
            @RequestParam int numOfApartments) {

        return travelAgencyService.calculatePrice(numAdults, numChildren, transportId, hotelId, duration, numOfSingleRooms, numOfDoubleRooms, numOfTripleRooms, numOfStudios, numOfApartments);
    }

    @PostMapping("/pay")
    public void pay(@RequestBody Long reservationId) {
        travelAgencyService.pay(reservationId);
    }
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
