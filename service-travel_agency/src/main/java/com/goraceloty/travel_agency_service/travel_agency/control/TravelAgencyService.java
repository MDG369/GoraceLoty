package com.goraceloty.travel_agency_service.travel_agency.control;

import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
//import com.goraceloty.travel_agency_service.travel_agency.entity.SeatDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service

public class TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;
    public final String transportServiceUrl = "http://service-flight:8080/transports/";
    public final String hotelServiceUrl = "http://service-hotel:8080/hotels/";
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

    private Integer fetchSeatDetails(String url, Long transportID) {
        String concat_url = url + "/" + transportID + "/seats";
        System.out.println("Requesting seat details from URL: " + concat_url);
        Integer seatDetails = restTemplate.getForObject(concat_url, Integer.class);
        System.out.println("Retrieved seat details: " + seatDetails);
        if (seatDetails != null) {
            double price = calculateTransportPrice(seatDetails);
            System.out.println(price);
            return seatDetails;
        } else {
            throw new IllegalStateException("Unable to retrieve seat details for transport ID: " + url);
        }
    }

    private Integer fetchStandardDetails(String url, Long hotelId) {
        String concat_url = url + hotelId + "/standard";
        System.out.println("Requesting seat details from URL: " + concat_url);
        Integer standard = restTemplate.getForObject(concat_url, Integer.class);
        System.out.println("Retrieved seat details: " + standard);
        return(standard);
    }

    public double calculatePrice(Long transportId, Long hotelId) {
        Integer seatDetails = fetchSeatDetails(transportServiceUrl, transportId);
        double transportPrice = calculateTransportPrice(seatDetails);
        System.out.println("Cena transportu" + transportPrice);

        Integer standard = fetchStandardDetails(hotelServiceUrl, hotelId);
        String roomSize = "dwuosobowy";
        double hotelPrice= calculateHotelPrice(standard, roomSize);
        System.out.println("Cena noclegu" + hotelPrice);

        double totalPrice = transportPrice + hotelPrice;

        return (totalPrice);
    }

    private double calculateTransportPrice(Integer seatDetails) {
        // Example pricing logic
        double baseTransportPrice = 500.0;
        return (baseTransportPrice * (2-seatDetails));
    }

    private double calculateHotelPrice(Integer standard, String roomSize) {
        // Example pricing logic
        double hotelPrice = 0;
        if(Objects.equals(roomSize, "dwuosobowy")){
            hotelPrice = 100 * standard;
        } else if (Objects.equals(roomSize, "trzyosobowy")){
            hotelPrice = 130 * standard;
        } else if(Objects.equals(roomSize, "czterosbowy")){
            hotelPrice = 150 * standard;
        } else if(Objects.equals(roomSize, "apartament")){
            hotelPrice = 200 * standard;
        }else if(Objects.equals(roomSize, "studio")){
            hotelPrice = 300 * standard;
        }
        return (hotelPrice);
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





