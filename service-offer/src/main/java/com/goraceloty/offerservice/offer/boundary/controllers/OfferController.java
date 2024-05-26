package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.OfferFilter;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

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

            JSONArray arrayHotel = new JSONArray(hotelsResponse.toString());
            JSONArray arrayTransport = new JSONArray(transportsResponse.toString());
            for(int i=0; i < arrayHotel.length(); i++)
            {
                JSONObject objectHotel = arrayHotel.getJSONObject(i);
                for(int j=0; j < arrayTransport.length(); j++) {
                    JSONObject objectTransport = arrayTransport.getJSONObject(j);
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
}
