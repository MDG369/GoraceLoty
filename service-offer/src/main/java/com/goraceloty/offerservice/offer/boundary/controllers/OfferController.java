package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.OfferFilter;
import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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

@Log
@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    @GetMapping
    List<Offer> getEx(OfferFilter offerFilter) {
        String hotelsResponse;
        String transportsResponse;
        List<Offer> offers = new ArrayList<>();
        Offer tmp;
        Long id = 0L;
        try {
            hotelsResponse = offerService.GetHotels(offerFilter);
            transportsResponse = offerService.GetTransports(offerFilter);
            log.info("Hotels: " + hotelsResponse);
            log.info(transportsResponse);
            JSONArray arrayHotel = new JSONArray(hotelsResponse.toString());
            JSONArray arrayTransport = new JSONArray(transportsResponse.toString());
            for(int i=0; i < arrayHotel.length(); i++)
            {

                JSONObject objectHotel = arrayHotel.getJSONObject(i);
                log.info("OBJHotel: " + objectHotel.toString());
                for(int j=0; j < arrayTransport.length(); j++) {
                    JSONObject objectTransport = arrayTransport.getJSONObject(j);
                    log.info("OBJTransport: " + objectTransport.toString());
                    tmp = new Offer();
                    tmp.setId(id);
                    tmp.setHotelID(parseLong(objectHotel.getString("hotelID")));
                    tmp.setTransportID(parseLong(objectTransport.getString("transportID")));
                    tmp.setCity(offerFilter.getCity());
                    tmp.setDateStart(offerFilter.getDateStart());
                    tmp.setDateEnd(offerFilter.getDateEnd());
                    tmp.setNumOfPeople(offerFilter.getNumOfPeople());
                    id++;

                    offers.add(tmp);
                    System.out.println(objectHotel.getString("hotelID") + " " + objectTransport.getString("transportID"));
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
    }

    @PostMapping
    public void startOfferBookingSaga(@RequestBody ReservationRequest reservationRequest) {
        // Send HttpRequest (POST) to orchestrator. It contains OfferId, HotelId, TransportId, Number of rooms of each type, date, numAdults, numChildren
        // Orchestrator sends message to Reservation(Travel Agency) service, the reservation is created with status PENDING.
        // Orchestrator sends messages to Transport and Hotel. If there is an error in either compensate Tran, Hotel and Res
        // If paid, Orchestrator sends message to Reservation to change status to paid, if 15 minutes pass remove reservation, compensate hotel and Transport
        offerService.tryBookingOffer(reservationRequest).block(Duration.ofSeconds(20));
    }
}
