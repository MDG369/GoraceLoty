package com.goraceloty.hotelservice.hotel.boundary.controllers;

import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.hotel.entity.SeatDataDTO;
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

    @GetMapping("/{id}/seats")
    public ResponseEntity<Double> getSeatData(@PathVariable Long id) {
        System.out.println("Zaczęto budować" + id);
        Transport transport = transportService.findTransportByID(id);
        System.out.println(transport);
        if (transport == null) {
            return ResponseEntity.notFound().build();
        }
        double seatsDifference = transport.getNumAvailableSeats() / transport.getNumTotalSeats();
        System.out.println("Seats difference for transport ID " + id + ": " + seatsDifference);

        return ResponseEntity.ok(seatsDifference);
//        System.out.println(transport.getNumTotalSeats());
//        SeatDataDTO seatDetails = new SeatDataDTO(transport.getNumTotalSeats(), transport.getNumAvailableSeats());
//        return ResponseEntity.ok(seatDetails);
    }
   /* @GetMapping("/matching/totalSeats")
    public List<Transport> getSeatsByExample(Transport transport) {
        return transportService.getSeatsByExample(transport);
    }*/
    @GetMapping
    public List<Transport> getAllTransports() {
        return transportService.getAllTransports();
    }
//    @PostMapping
//    public ResponseEntity<Transport> createOrUpdateTransport(@RequestBody Transport transport) {
//        Transport savedTransport = transportService.saveTransport(transport);
//        return ResponseEntity.ok(savedTransport);
//    }
}
