package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.Offer;
import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import com.goraceloty.offerservice.offer.entity.OfferFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log
@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final WebClient webClient;

    public OfferService(WebClient.Builder webClientBuilder, OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8084")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public List<Offer> getOffersByExample(Offer offer) {
        final ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        final Example<Offer> example = Example.of(offer, matcher);
        List<Offer> results;
        results = offerRepository.findAll(example);

        return results;
    }

    private String sendGet(URL url) {
        HttpURLConnection con = null;
        try {
            System.out.println("sendGet: " + url);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }
        }
        catch (Exception e){
            return "fail";
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return "fail";
    }
    public Boolean getHotelAvailability(Long id, Integer numOfPeople) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty()) {
            return false;
        }

        URL url;
        try {
            url = new URL(String.format(
                    "http://service-hotel:8080/hotels/availability?dateStart=%s&dateEnd=%s&hotelID=%d&numOfPeople=%d",
                    offer.get().getDateStart(), offer.get().getDateEnd(), offer.get().getHotelID(), numOfPeople));
            System.out.println("getHotelAvailability getting url: " + url);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException " + e);
            return false;
        }

        String response = sendGet(url);
        System.out.println("Response from hotel availability: " + response);

        if(response.equals("fail")) {
            System.out.println("Request to hotel availability returned fail, perhaps wrong url?");
            return false;
        }
        else if (response.equals("false")) {
            System.out.println("Request to hotel availability returned false");
            return false;
        }
        else if (response.equals("true")) {
            System.out.println("Request to hotel availability returned true");
            return true;
        }

        return false;
    }

    public Boolean getTransportAvailability(Long id, Integer numOfPeople) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty()) {
            return false;
        }

        URL url;
        try {
            url = new URL(String.format(
                    "http://service-flight:8082/transports/%d/seats", offer.get().getTransportID()));
            System.out.println("getTransportAvailability getting url: " + url);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException " + e);
            return false;
        }

        String response = sendGet(url);
        System.out.println("Response from transport seats: " + response);
        Integer numOfSeats;

        try {
            numOfSeats = Integer.valueOf(response);
            System.out.println("getTransportAvailability numOfSeats returned: " + numOfSeats);
        }
        catch (NumberFormatException e) {
            System.out.println("getTransportAvailability Integer conversion FAILED");
            return false;
        }

        if (numOfSeats >= numOfPeople) {
            return true;
        }

        return false;
    }

    public void handleTransportFull(Long id) {
        offerRepository.deleteById(id);
    }



    public Boolean getAvailability(Long id, Integer numOfPeople) {
        boolean availability = getTransportAvailability(id, numOfPeople);
        availability = availability && getHotelAvailability(id, numOfPeople);

        return availability;
    }

    /*public String GetTransportss(OfferFilter offerFilter) throws IOException, JSONException {
        URL url;
        if(offerFilter.getCity() != null) {
            System.out.println("not null");
            url = new URL("http://service-flight:8082/transports/matching?cityArrival=" + offerFilter.getCity().replaceAll(" ", "%20"));
        }
        else {
            System.out.println("null ");
            url = new URL("http://service-flight:8082/transports");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return "[\"hotel\" : none]";
    }*/

    public String BuildOffer(OfferFilter offerFilter) {
        return "OK";
    }

    public Long createOffer(Offer offer) {
        return offerRepository.save(offer).getId();
    }

    public void updateOfferName(Long offerId, String name) {

    }

    public Mono<Long> tryBookingOffer(ReservationRequest reservationRequest) {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/booking")
                        .build())
                .body(Mono.just(reservationRequest), ReservationRequest.class)
                .retrieve()
                .bodyToMono(Long.class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Initiating booking saga failed with error: " + e);
                    return Mono.empty();
                });
    }

}
