package com.goraceloty.hotelservice.hotel.boundary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    @GetMapping
    ResponseEntity<String> getEx() {
        return ResponseEntity.ok().body("\"Connection to hotel service works!\"");
    }
}
