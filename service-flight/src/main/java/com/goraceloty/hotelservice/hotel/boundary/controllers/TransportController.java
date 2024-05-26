package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transports")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    @GetMapping("/connections")
    ResponseEntity<String> getEx() {
        return ResponseEntity.ok().body("\"Connection to transport service works!\"");
    }
    @GetMapping("/matching")
    public List<Transport> getTransportByExample(Transport transport) {
        return transportService.getTransportByExample(transport);
    }
   /* @GetMapping("/matching/totalSeats")
    public List<Transport> getSeatsByExample(Transport transport) {
        return transportService.getSeatsByExample(transport);
    }*/
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
