package com.goraceloty.travel_agency_service.travel_agency.control;

import com.goraceloty.travel_agency_service.saga.entity.ReservationRequest;
import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import com.goraceloty.apigateway.travel_agency.entity.PriceObject;
//import com.goraceloty.travel_agency_service.travel_agency.entity.SeatDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service

public class TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;
    public final String transportServiceUrl = "http://service-flight:8082/";
    //public final String transportServiceUrl = "http://localhost:8082/";
    public final String hotelServiceUrl = "http://service-hotel:8080/";
    //public final String hotelServiceUrl = "http://localhost:8080/";
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

    private double fetchSeatDetails(String url, Long transportID) {
        String concatUrl = String.format("%s/%d/seats", url, transportID);
        System.out.println("Requesting seat details from URL: " + concatUrl);

        Double seatDetails = null;
        try {
            seatDetails = restTemplate.getForObject(concatUrl, Double.class);
            System.out.println("Retrieved seat details: " + seatDetails);
        } catch (Exception e) {
            System.err.println("Error retrieving seat details: " + e.getMessage());
            throw new IllegalStateException("Unable to retrieve seat details for transport ID: " + transportID, e);
        }

        if (seatDetails != null) {
            double price = calculateTransportPrice(seatDetails);
            System.out.println("Calculated price based on seat details: " + price);
            return seatDetails;
        } else {
            throw new IllegalStateException("No seat details were found for transport ID: " + transportID);
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

    public double calculatePrice(PriceObject priceObject) {
        //OfferReservation reservation = fetchReservationById(reservationId);
        //if (reservation == null) {
           // throw new IllegalArgumentException("No reservation found with ID: " + reservationId);
       // }
        //Integer numAdults = reservation.getNumAdult();
        int totalPeople = priceObject.getNumChildren() + priceObject.getNumAdults();
        double groupDiscount = getGroupDiscount(priceObject.getNumAdults(), priceObject.getNumChildren());;
        double childrenDiscount = getChildrenDiscount(priceObject.getNumChildren());
        double seatDetails = fetchSeatDetails(transportServiceUrl, priceObject.getTransportId());
        double transportPrice = calculateTransportPrice(seatDetails);
        double basePrice = 100.0;
        Integer standard = fetchStandardDetails(hotelServiceUrl, priceObject.getHotelId());
        double hotelPrice= calculateHotelPrice(standard, totalPeople);

        //total price computation
        double totalDiscount = childrenDiscount * groupDiscount;
        double totalBasePrice = basePrice * priceObject.getDuration() * totalPeople;
        double totalTransportPrice = transportPrice * totalPeople;
        double totalHotelPrice =  totalPeople * priceObject.getDuration();

        System.out.println("Cena transportu" + transportPrice);
        System.out.println("Cena noclegu" + hotelPrice);
        System.out.println("Cena bazowa" + totalBasePrice);
        System.out.println("Zniżka" + totalDiscount);

        double totalPrice =  totalDiscount * (totalBasePrice+totalTransportPrice+totalHotelPrice);
        BigDecimal bd = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);
        double roundedTotalPrice = bd.doubleValue();
        System.out.println("Cena całkowita" + totalPrice);
        return (roundedTotalPrice);
    }

    private double calculateTransportPrice(double seatDetails) {
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

    public static int calculateTripDuration(String dateStart, String dateEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the start and end dates from the given strings
        LocalDate startDate = LocalDate.parse(dateStart, formatter);
        LocalDate endDate = LocalDate.parse(dateEnd, formatter);

        // Calculate the number of days between the start and end dates
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate);

        return days;
    }
    public void pay(Long reservationId) {
        OfferReservation reservation = fetchReservationById(reservationId);
        reservation.setIsPaid(true);
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






