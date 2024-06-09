package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.OfferFilter;
import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
// todo Dodanie z powrotem offers, hotfix bo front nie działał
@RequestMapping("")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private RabbitTemplate rabbitTemplate;
    @GetMapping
    List<Offer> getOffers(OfferFilter offerFilter) {
        return offerService.getOffers();
    }

    // example of valid command
    // http://localhost:8081/offers/hotel?id=1&numOfPeople=2
    // getting hotel availability
    // id - offer if
    // numOfPeople - number of people 1-3 is a valid input representing single, double and triple room
    @GetMapping("/hotel")
    Boolean getHotelAvailability(Long id, Integer numOfPeople) {
        return offerService.getHotelAvailability(id, numOfPeople);
    }
    @PostMapping("/book/offer")
    public ResponseEntity<String> bookOffer() {
        // Process the booking
        List<String> attributes = Arrays.asList("flightAvailability", "hotelAvailability", "basePrice");  // Example attributes

        Offer updatedOffer = offerService.getRandomOfferDetails(attributes);
        if (updatedOffer == null) {
            return ResponseEntity.badRequest().body("No offers available or booking failed.");
        }

        return ResponseEntity.ok("Booking change ID: " + updatedOffer.getId());
    }

    // example
    // http://localhost:8081/offers/transport?id=1&numOfPeople=2
    // getting transport availability, checks if numOfSeats >= numOfPeople
    // id - offer id
    // numOfPeople - number of people, any number valid
    @GetMapping("/transport")
    Boolean getTransportAvailability(Long id, Integer numOfPeople) {
        return offerService.getTransportAvailability(id, numOfPeople);
    }

    // Logical AND on transport and hotel availability
    // http://localhost:8081/offers/availability?id=1&numOfPeople=1
    @GetMapping("/availability")
    Boolean getAvailability(Long id, Integer numOfPeople) {
        return offerService.getAvailability(id, numOfPeople);
    }

    @GetMapping("/matching")
    public List<Offer> getOfferByExample(Offer offer) {
        return offerService.getOffersByExample(offer);
    }

    @GetMapping("/event")
    public String getTransportFull(String event, Long id) {

        System.out.println("event received: " + event + " id: " + id);

        if(event.equals("transportFull")) {
            System.out.println("transportFull received");

            offerService.handleTransportFull(id);
        }

        return "event received";
    }


    @PostMapping
    public void startOfferBookingSaga(@RequestBody ReservationRequest reservationRequest) {
        // Send HttpRequest (POST) to orchestrator. It contains OfferId, HotelId, TransportId, Number of rooms of each type, date, numAdults, numChildren
        // Orchestrator sends message to Reservation(Travel Agency) service, the reservation is created with status PENDING.
        // Orchestrator sends messages to Transport and Hotel. If there is an error in either compensate Tran, Hotel and Res
        // If paid, Orchestrator sends message to Reservation to change status to paid, if 15 minutes pass remove reservation, compensate hotel and Transport
        offerService.tryBookingOffer(reservationRequest);
    }
}
