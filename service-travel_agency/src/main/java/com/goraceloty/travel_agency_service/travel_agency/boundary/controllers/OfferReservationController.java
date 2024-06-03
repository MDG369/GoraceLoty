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
    public double getAdjustedPrice(@RequestParam Long reservationId, int numAdults) {
        return travelAgencyService.calculatePrice(reservationId, numAdults);
    }
    @PutMapping("/{id}/incrementAdults")
    public ResponseEntity<?> incrementAdults(@PathVariable Long id) {
        try {
            travelAgencyService.incrementNumAdults(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }

