package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    @GetMapping
    String getEx(Offer offer) {
        String out;
        try {
            out = offerService.GetHotels(offer);
        } catch (IOException e) {
            out = "Failed to read hotels!";
        }

        try {
            out += offerService.GetTransports(offer);
        } catch (IOException e) {
            out += "Failed to read transports!!";
        }
        return out;
    }

    @PostMapping
    public void startOfferBookingSaga(ReservationRequest reservationRequest) {
        // Send HttpRequest (POST) to orchestrator. It contains OfferId, HotelId, TransportId, Number of rooms of each type, date, numAdults, numChildren
        // Orchestrator sends message to Reservation(Travel Agency) service, the reservation is created with status PENDING.
        // Orchestrator sends messages to Transport and Hotel. If there is an error in either compensate Tran, Hotel and Res
        // If paid, Orchestrator sends message to Reservation to change status to paid, if 15 minutes pass remove reservation, compensate hotel and Transport
        offerService.tryBookingOffer(reservationRequest);
    }
}
