package com.goraceloty.apigateway.hotels.boundary.controllers;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.control.HotelClient;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import com.goraceloty.apigateway.saga.entity.ChangeMessage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("hotels")
public class HotelController {

    private static final int BLOCK_TIME = 30; // 30s block time
    private final AppProperties appProperties;
    private final HotelClient hotelClient;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("health")
    public String healthCheck() {
        // Zostawiam tak żeby można było sprawdzać czy websockety działąją
        messagingTemplate.convertAndSend("/topic/changes",  new ChangeMessage("Test", 1L, 1L, 1L));
        return appProperties.getHotel();
    }

    @GetMapping
    public List<Hotel> getHotelsByExample(Hotel hotel) {
        var res = hotelClient.getHotelsByExample(hotel).block(Duration.ofSeconds(BLOCK_TIME));

        return res != null ? Arrays.stream(res).collect(Collectors.toList()): null;
    }
}
