package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.OfferFilter;
import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;
import java.time.Duration;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
   /* @GetMapping
    List<Offer> getEx(OfferFilter offerFilter) {
        String hotelsResponse;
        String transportsResponse;
        List<Offer> offers = new ArrayList<>();
        Offer tmp;
        Long id = 0L;
        try {
            hotelsResponse = offerService.GetHotels(offerFilter);
            System.out.println(hotelsResponse);
            transportsResponse = offerService.GetTransportss(offerFilter);
            System.out.println(transportsResponse);

            JSONArray arrayHotel = new JSONArray(hotelsResponse);
            JSONArray arrayTransport = new JSONArray(transportsResponse);
            System.out.println("arrayHotel length: " + arrayHotel.length());
            System.out.println("arrayTransport length: " + arrayTransport.length());
            for(int i=0; i < arrayHotel.length(); i++)
            {
                System.out.println("JSON loop ");
                JSONObject objectHotel = arrayHotel.getJSONObject(i);
                System.out.println(objectHotel.getLong("hotelID"));
                for(int j=0; j < arrayTransport.length(); j++) {
                    JSONObject objectTransport = arrayTransport.getJSONObject(j);
                    tmp = new Offer();
                    tmp.setId(id);
                    tmp.setHotelID(objectHotel.getLong("hotelID"));
                    tmp.setTransportID(objectTransport.getLong("transportID"));
                    tmp.setCityDeparture(offerFilter.getCity());
                    tmp.setDateStart(offerFilter.getDateStart());
                    tmp.setDateEnd(offerFilter.getDateEnd());
                    id++;

                    offers.add(tmp);
                    System.out.println(objectHotel.getLong("hotelID") + " " + objectTransport.getLong("transportID"));
                }
            }
        } catch (IOException | JSONException e) {
            hotelsResponse = "";
        }

        //try {
        //    transportsResponse = offerService.GetTransports(offerFilter);
        //} catch (IOException e) {
        //    transportsResponse = "";
        //}

        return offers;
    }*/
    @GetMapping
    List<Offer> getOffers(OfferFilter offerFilter) {
        return offerService.getOffers();
    }

    // example of valid command
    // http://localhost:8081/offers/hotel?id=1&numOfPeople=2
    // id - offer if
    // numOfPeople - number of people 1-3 is a valid input representing single, double and triple room
    @GetMapping("/hotel")
    Boolean getHotelAvailability(Long id, Integer numOfPeople) {
        return offerService.getHotelAvailability(id, numOfPeople);
    }

    @GetMapping("/matching")
    public List<Offer> getOfferByExample(Offer offer) {
        return offerService.getOffersByExample(offer);
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
