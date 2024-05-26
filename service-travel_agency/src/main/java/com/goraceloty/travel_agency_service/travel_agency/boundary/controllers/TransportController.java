package com.goraceloty.travel_agency_service.travel_agency.boundary.controllers;

import com.goraceloty.travel_agency_service.travel_agency.entity.TransportObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class TransportController {
    @PostMapping
    public ResponseEntity<String> receiveData(@RequestBody TransportObject data) {
        // Process the received data
        System.out.println("Received data: " + data.toString());
        // Return a response
        return ResponseEntity.ok("Data received successfully");
    }
}
