package com.goraceloty.apigateway.hotels.boundary.controllers;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.control.HotelClient;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController("hotels")
public class HotelController {

    private static final int BLOCK_TIME = 30; // 30s block time
    private final AppProperties appProperties;
    private final HotelClient hotelClient;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("health")
    public String healthCheck() {
        return appProperties.getHotel();
    }

    @GetMapping
    public List<Hotel> getHotelsByExample(Hotel hotel) {
        return Arrays.stream(hotelClient.getHotelsByExample(hotel).block(Duration.ofSeconds(BLOCK_TIME))).collect(Collectors.toList());
    }
}
