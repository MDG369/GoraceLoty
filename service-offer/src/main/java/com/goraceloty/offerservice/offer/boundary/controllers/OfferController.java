package com.goraceloty.offerservice.offer.boundary.controllers;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
}
