package com.goraceloty.apigateway.saga.boundary.controllers;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.control.HotelClient;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import com.goraceloty.apigateway.saga.control.SagaClient;
import com.goraceloty.apigateway.saga.entity.ChangeMessage;
import com.goraceloty.apigateway.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("booking")
public class SagaController {

    private static final int BLOCK_TIME = 30; // 30s block time
    private final AppProperties appProperties;
    private final SagaClient sagaClient;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("health")
    public String healthCheck() {
        // Zostawiam tak żeby można było sprawdzać czy websockety działąją
        messagingTemplate.convertAndSend("/topic/changes",  new ChangeMessage("Test", 1L, 1L, 1L));
        return appProperties.getHotel();
    }

    @PostMapping
    public Long makeBooking(ReservationRequest request) {
        var res = sagaClient.makeBooking(request).block(Duration.ofSeconds(BLOCK_TIME));
        return res;
    }

}
