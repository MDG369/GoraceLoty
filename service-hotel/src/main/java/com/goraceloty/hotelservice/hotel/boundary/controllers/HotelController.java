package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getHotels();
    }

    @PostMapping
    public void createHotel(@RequestBody Hotel hotel) {
        hotelService.addHotel(hotel);
    }

    @DeleteMapping
    public void deleteHotel(@RequestBody Long id) {
        hotelService.removeHotelById(id);
    }

    @GetMapping("/stars")
    public Hotel getHotelsByStars(@RequestBody int stars) {
        return hotelService.getHotelByStars(stars);
    }

    @GetMapping("/matching")
    public List<Hotel> getHotelByExample(Hotel hotel) {
        return hotelService.getHotelsByExample(hotel);
    }
}
