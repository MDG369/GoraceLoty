package com.goraceloty.travel_agency_service.travel_agency.control;

import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import com.goraceloty.travel_agency_service.travel_agency.entity.SeatDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@Service

public class TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;
    private final String transportServiceUrl = "http://service-flight:8082/transports/";
    private final RestTemplate restTemplate;

//    public List<com.goraceloty.travel_agencyservice.travel_agency.entity.Clients> getTransport() {
//        return transportRepository.findAll();
//    }
//
//    public void addTransport(com.goraceloty.travel_agencyservice.travel_agency.entity.Clients transport) {
//        transportRepository.save(transport);
//    }
//
//    public void removeTransportById(Long id) {
//        transportRepository.delete(transportRepository.findById(id).orElseThrow());
//    }

//
    public List<OfferReservation> getOfferReservationByExample(OfferReservation offerReservation) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<OfferReservation> example = Example.of(offerReservation, matcher);
        List<OfferReservation> results;
        results = travelAgencyRepository.findAll(example);
        //filtrowanie po datach
        return results;
    }

    public double calculatePriceBasedOnSeats(Long transportId) {
        String url = UriComponentsBuilder.fromHttpUrl(transportServiceUrl).path(transportId + "/seats").toUriString();
        System.out.println(url);
        Integer seatDetails = restTemplate.getForObject(url, Integer.class);
        System.out.println(seatDetails);
        if (seatDetails != null) {
            double price = calculatePrice(seatDetails);
            System.out.println(price);
            return price;
        } else {
            throw new IllegalStateException("Unable to retrieve seat details for transport ID: " + transportId);
        }
    }

    private double calculatePrice(Integer seatDetails) {
        // Example pricing logic
        return seatDetails * 100 - seatDetails* 50;
    }

    /*public double adjustPriceBasedOnSeats(Long transportId) {
        int availableSeats = getAvailableSeats(transportId);
        double basePrice = 500.0;
        double transportPrice = 500.0; // This could be dynamic based on some other factors.
        double priceAdjustmentFactor = availableSeats < 50 ? 1.1 : 1.0; // Simple logic to adjust price based on seat availability

        return (basePrice + transportPrice) * priceAdjustmentFactor;
    }*/
//    public double adjustPrice(OfferReservation offerReservation, Double transport) {
//        Double base_price = 100.0; //ilość dni* 100
//        Double transport_price = 500.0;
//
//        Double Adjusted_price = base_price + transport_price;
//        System.out.println(Adjusted_price);
//        return Adjusted_price;
//    }
}





