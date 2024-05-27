package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Transport;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service

public class TransportService {
    private final TransportRepository transportRepository;

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    public void addTransport(Transport transport) {
        transportRepository.save(transport);
    }

    public void removeTransportById(Long id) {
        transportRepository.delete(transportRepository.findById(id).orElseThrow());
    }

    public List<Transport> getTransportByExample(Transport transport) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<Transport> example = Example.of(transport, matcher);
        List<Transport> results;
        results = transportRepository.findAll(example);
        return results;
    }

    public Transport findTransportByID(Long id) {
        Optional<Transport> transport = transportRepository.findById(id);
        return transport.orElse(null);
    }

    public void bookTransport(ReservationRequest reservationRequest) {
        Transport transport = findTransportByID(reservationRequest.getTransportID());
        Integer seatsToBook =  reservationRequest.getNumOfAdults() + reservationRequest.getNumOfChildren();
        if (transport.getNumAvailableSeats() < seatsToBook) {
            throw new IllegalArgumentException("Number of booked seats exceeds the number of available seats");
        }
        transport.setNumAvailableSeats(transport.getNumAvailableSeats() - seatsToBook);
        transportRepository.save(transport);
    }

    public void cancelBookingTransport(ReservationRequest reservationRequest) {
        Transport transport = findTransportByID(reservationRequest.getTransportID());
        Integer seatsToBook =  reservationRequest.getNumOfAdults() + reservationRequest.getNumOfChildren();
        transport.setNumAvailableSeats(transport.getNumAvailableSeats() + seatsToBook);
        transportRepository.save(transport);
    }


//    public List<Transport> getSeatsByExample(Transport transport) {
//        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        final Example<Transport> example = Example.of(transport, matcher);
//        List<Transport> results;
//        results = transportRepository.findById(example);
//        return results;
//    }

   // @Autowired
    //private RestTemplate restTemplate;

//    public void sendData(Transport data) {
//        String url = "http://localhost:8380/api/data";
//        ResponseEntity<String> response = restTemplate.postForEntity(url, "test", String.class);
//        System.out.println("Response from server: " + response.getBody());
//    }


}





