package com.goraceloty.travel_agency_service.travel_agency.control;

import com.goraceloty.travel_agency_service.saga.entity.ReservationRequest;
import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
//import com.goraceloty.travel_agency_service.travel_agency.entity.SeatDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service

public class TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;
    //public final String transportServiceUrl = "http://service-flight:8080/transports/";
    public final String transportServiceUrl = "http://localhost:8082/";
    //public final String hotelServiceUrl = "http://service-hotel:8080/hotels/";
    public final String hotelServiceUrl = "http://localhost:8080/";
    private final RestTemplate restTemplate;


//    public List<com.goraceloty.travel_agencyservice.travel_agency.entity.Clients> getTransport() {
//        return transportRepository.findAll();
//    }
//

    public Optional<OfferReservation> getOfferReservationById(Long id) {
        return travelAgencyRepository.findById(id);
    }

    public OfferReservation addReservation(OfferReservation offerReservation) {
        return travelAgencyRepository.save(offerReservation);
    }

    public void removeReservationById(Long id) {
        travelAgencyRepository.delete(travelAgencyRepository.findById(id).orElseThrow());
    }

//
    private OfferReservation fetchReservationById(Long reservationId) {
        Optional<OfferReservation> transport = travelAgencyRepository.findById(reservationId);
        return transport.orElse(null);
    }

    public List<OfferReservation> getOfferReservationByExample(OfferReservation offerReservation) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("reservationID");
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

    public double getGroupDiscount(int numAdults, int numChildren) {
        int totalPeople = numAdults + numChildren;
        return totalPeople > 3 ? 0.85 : 1.0;
    }

    public double getChildrenDiscount(int numChildren) {
        return numChildren > 0 ? 0.95 : 1.0;
    }

    public double calculatePrice(Long reservationId, int numAdults) {
        OfferReservation reservation = fetchReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("No reservation found with ID: " + reservationId);
        }
        //Integer numAdults = reservation.getNumAdult();
        Integer numChildren = reservation.getNumChildren();
        Integer totalPeople = numChildren + numAdults;
        Long transportId = reservation.getTransportID();
        Long hotelId = reservation.getTransportID();
        int duration = reservation.getNumOfDays();
        double groupDiscount = getGroupDiscount(numAdults, numChildren);;
        double childrenDiscount = getChildrenDiscount(numChildren);
        Integer seatDetails = fetchSeatDetails(transportServiceUrl, transportId);
        double transportPrice = calculateTransportPrice(seatDetails);
        double basePrice = 100.0;
        Integer standard = fetchStandardDetails(hotelServiceUrl, hotelId);
        double hotelPrice= calculateHotelPrice(standard, totalPeople);

        //total price computation
        double totalDiscount = childrenDiscount * groupDiscount;
        double totalBasePrice = basePrice * duration * totalPeople;
        double totalTransportPrice = transportPrice * totalPeople;
        double totalHotelPrice =  totalPeople * duration;

        System.out.println("Cena transportu" + transportPrice);
        System.out.println("Cena noclegu" + hotelPrice);
        System.out.println("Cena bazowa" + totalBasePrice);
        System.out.println("Zniżka" + totalDiscount);

        double totalPrice =  totalDiscount * (totalBasePrice+totalTransportPrice+totalHotelPrice);
        System.out.println("Cena całkowita" + totalPrice);
        reservation.setAdjustedPrice(totalPrice);
        return (totalPrice);
    }

    private double calculateTransportPrice(Integer seatDetails) {
        // Example pricing logic
        System.out.println(seatDetails);
        double baseTransportPrice = 500.0;
        return (baseTransportPrice * (2.0-seatDetails));
    }

    private double calculateHotelPrice(Integer standard, Integer roomSize) {
        // Example pricing logic
        double hotelPrice = 0;
        if(Objects.equals(roomSize, 2)){
            hotelPrice = 100 * standard;
        } else if (Objects.equals(roomSize, 3)){
            hotelPrice = 130 * standard;
        } else if(Objects.equals(roomSize, 4)){
            hotelPrice = 150 * standard;
        } else if(Objects.equals(roomSize, 5)){
            hotelPrice = 200 * standard;
        }else if(roomSize >= 6) {
            hotelPrice = 300 * standard;
        }
        return (hotelPrice);
    }

    public OfferReservation getOfferReservationByReservationRequest(ReservationRequest reservationRequest) throws Exception {
        OfferReservation offerReservation = new OfferReservation();
        offerReservation.createOfferReservationFromReservationRequest(reservationRequest);
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("reservationID", "reservationTime", "isPaid").withIgnoreNullValues();
        final Example<OfferReservation> example = Example.of(offerReservation, matcher);
        return travelAgencyRepository.findAll(example, Sort.by(Sort.Direction.DESC, "reservationTime")).getFirst();
    }
    public void incrementNumAdults(Long reservationId) {
        OfferReservation reservation = travelAgencyRepository.findById(reservationId)
                    .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));
            reservation.incrementAdults();
            travelAgencyRepository.save(reservation);
        }
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






