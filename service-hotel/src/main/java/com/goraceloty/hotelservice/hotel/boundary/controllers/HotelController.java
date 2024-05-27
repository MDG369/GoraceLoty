package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    ResponseEntity<String> getEx() {
        return ResponseEntity.ok().body("\"Connection to hotel service works!\"");
    }

    @GetMapping("/{id}/standard")
    public ResponseEntity<Integer> getSeatData(@PathVariable Long id) {
        System.out.println("Zaczęto budować" + id);
        Hotel hotel = hotelService.findHotelByID(id);
        System.out.println(hotel);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        int standard = hotel.getStandard();
        System.out.println("Seats difference for transport ID " + id + ": " + standard);

        return ResponseEntity.ok(standard);
//        System.out.println(transport.getNumTotalSeats());
//        SeatDataDTO seatDetails = new SeatDataDTO(transport.getNumTotalSeats(), transport.getNumAvailableSeats());
//        return ResponseEntity.ok(seatDetails);
    }
}
