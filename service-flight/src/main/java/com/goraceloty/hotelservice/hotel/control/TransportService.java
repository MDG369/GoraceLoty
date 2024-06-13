package com.goraceloty.hotelservice.hotel.control;

import com.goraceloty.hotelservice.hotel.entity.Transport;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service

public class TransportService {
    private final TransportRepository transportRepository;
    private final RestTemplate restTemplate = new RestTemplate();

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

    public int getAvailableSeats(Long flightId) {
        Transport transport = findTransportByID(flightId);
        return transport.getNumAvailableSeats();
    }

    // Method to get the total number of seats for a given flight ID
    public int getNumTotalSeats(Long flightId) {
        Transport transport = findTransportByID(flightId);
        return transport.getNumTotalSeats();
    }

    // Method to set the total number of seats for a given flight ID
    @Transactional
    public void setTotalSeats(Long flightId, int seats) {
        Transport transport = findTransportByID(flightId);
        transport.setNumTotalSeats(seats);
        transportRepository.save(transport);
    }

    public void bookTransport(ReservationRequest reservationRequest) throws MalformedURLException {
        Transport transport = findTransportByID(reservationRequest.getTransportID());
        Integer seatsToBook =  reservationRequest.getNumOfAdults() + reservationRequest.getNumOfChildren();
        if (transport.getNumAvailableSeats() < seatsToBook) {
            throw new IllegalArgumentException("Number of booked seats exceeds the number of available seats");
        }
        transport.setNumAvailableSeats(transport.getNumAvailableSeats() - seatsToBook);

        // if number of available seats is 0 emit transportFull event
        if(transport.getNumAvailableSeats() == 0) {
            System.out.println("id: " + reservationRequest.getOfferID());
            URL url = new URL(String.format("http://service-offer:8081/offers/event?event=%s&id=%d", "transportFull", reservationRequest.getOfferID()));
            String response = restTemplate.sendGet(url);
            System.out.println("event response: " + response);
            ResponseEntity.ok("Event sent to service-offer: " + response);

        }
        transportRepository.save(transport);
    }

    public void cancelBookingTransport(ReservationRequest reservationRequest) {
        Transport transport = findTransportByID(reservationRequest.getTransportID());
        Integer seatsToBook =  reservationRequest.getNumOfAdults() + reservationRequest.getNumOfChildren();
        transport.setNumAvailableSeats(transport.getNumAvailableSeats() + seatsToBook);
        transportRepository.save(transport);
    }


    public void addAvailableSeats(Long flightId, int seatsToAdd) {
        Transport transport = transportRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + flightId));

        int currentSeats = transport.getNumAvailableSeats() + seatsToAdd;
        transport.setNumTotalSeats(currentSeats + seatsToAdd);

        transportRepository.save(transport);

        //logger.info("Added {} seats to flight ID {}. Total available seats now: {}", seatsToAdd, flightId, flight.getAvailableSeats());
    }

    public void adjustPrice(Long flightId, int valueToAdjust) {
        Transport transport = findTransportByID(flightId);
        int newPrice = transport.getNumBasePrice() + valueToAdjust;
        transport.setNumBasePrice(newPrice);
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





