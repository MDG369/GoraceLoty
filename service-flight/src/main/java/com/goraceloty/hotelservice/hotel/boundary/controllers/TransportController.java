package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.hotel.control.TransportRepository;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/transports")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;

    @GetMapping
    ResponseEntity<String> getEx() {
        return ResponseEntity.ok().body("\"Connection to hotel service works!\"");
    }
    @GetMapping
    public List<Transport> getAllTransports() {
        return transportService.getTransport();
    }
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
