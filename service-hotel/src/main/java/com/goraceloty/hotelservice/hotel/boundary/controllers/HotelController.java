package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.AvailabilityService;
import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.hotel.entity.AvailabilityFilter;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.hotel.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final AvailabilityService availabilityService;

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

    @PutMapping("/availability")
    public String generateHotelAvailability() {
        return hotelService.generateHotelAvailability();
    }

    @GetMapping("/availability")
    public Boolean getAvailability(AvailabilityFilter filter) {
        return availabilityService.getAvailability(filter);
    }
    // example
    // http://localhost:8080/hotels/availability?dateStart=2024-06-07&dateEnd=2024-06-17&hotelID=5&numOfPeople=2

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
