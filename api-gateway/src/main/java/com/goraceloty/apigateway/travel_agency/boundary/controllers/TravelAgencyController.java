package com.goraceloty.apigateway.travel_agency.boundary.controllers;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.control.HotelClient;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import com.goraceloty.apigateway.saga.entity.ChangeMessage;
import com.goraceloty.apigateway.travel_agency.control.TravelAgencyClient;
import com.goraceloty.apigateway.travel_agency.entity.OfferReservation;
import com.goraceloty.apigateway.travel_agency.entity.PriceObject;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("travelagency")
public class TravelAgencyController {
    private static final int BLOCK_TIME = 30; // 30s block time
    private final AppProperties appProperties;
    private final TravelAgencyClient travelAgencyClient;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("health")
    public String healthCheck() {
        // Zostawiam tak żeby można było sprawdzać czy websockety działąją
        messagingTemplate.convertAndSend("/topic/changes",  new ChangeMessage("Test", 1L, 1L, 1L));
        return appProperties.getTravelagency();
    }

    @GetMapping()
    public List<OfferReservation> getAllOfferReservations() {
        var res = travelAgencyClient.getAllReservations().block(Duration.ofSeconds(BLOCK_TIME));
        return Arrays.asList(res);
    }

    @GetMapping("price")
    public Double getPrice(@RequestBody PriceObject priceObject) {
        var res = travelAgencyClient.getPrice(priceObject).block(Duration.ofSeconds(BLOCK_TIME));
        return res;
    }
}
