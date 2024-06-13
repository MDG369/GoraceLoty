package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.*;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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
>>>>>>> origin/main
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Log
@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final OfferChangeRepository offerChangeRepository;
    private final WebClient webClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public OfferService(WebClient.Builder webClientBuilder, OfferRepository offerRepository, OfferChangeRepository offerChangeRepository) {
        this.offerRepository = offerRepository;
        this.webClient = webClientBuilder
<<<<<<< HEAD
                .baseUrl("http://saga-orchestrator:8084")
=======
                .baseUrl("http://localhost:8084")
>>>>>>> origin/main
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
        this.offerChangeRepository = offerChangeRepository;
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

    public List<OfferChange> getOffersChangesByExample() {
        List<OfferChange> results;
        results = offerChangeRepository.findAll();

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
        } catch (Exception e) {
            return "fail";
        } finally {
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

        if (response.equals("fail")) {
            System.out.println("Request to hotel availability returned fail, perhaps wrong url?");
            return false;
        } else if (response.equals("false")) {
            System.out.println("Request to hotel availability returned false");
            return false;
        } else if (response.equals("true")) {
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
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isPresent()) {
            System.out.println("Updated offer availability " + id);
            offer.get().setAvailable(false);
        }
        else {
            System.out.println("Could not find offer " + id);
        }
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
<<<<<<< HEAD
    private final Logger logger = LoggerFactory.getLogger(OfferService.class);

    public Offer getRandomOfferDetails(List<String> attributes) {
        List<Offer> offers = offerRepository.findAll();
        if (offers.isEmpty()) {
            return null; // Alternatively, throw a more specific exception if needed
        }

        Offer randomOffer = getRandomOffer(offers);
        String attributeToModify = getRandomAttribute(attributes);
        OfferMessage offerMessage = createOfferMessage(randomOffer, attributeToModify);
        ChangeMessage changeMessageToSend = new ChangeMessage();
        ChangeMessage changeMessage = createChangeMessage(randomOffer, changeMessageToSend);

        logAndSaveOfferChanges(randomOffer, offerMessage, attributeToModify);
        sendMessage(offerMessage);
        sendSocketMessage(changeMessage);
        //sendSocketMessage(ChangeMessage changeMessage)
        return randomOffer;
    }

    private Offer getRandomOffer(List<Offer> offers) {
        return offers.get(new Random().nextInt(offers.size()));
    }

    private String getRandomAttribute(List<String> attributes) {
        return attributes.get(new Random().nextInt(attributes.size()));
    }

    private OfferMessage createOfferMessage(Offer offer, String attributeToModify) {
        OfferMessage offerMessage = new OfferMessage();
        String logMessage = "Modified ";
        String messageKey = "";
        String exchange = "";

        switch (attributeToModify) {
            case "flightAvailability":
                adjustTransportAvailability(offer, offerMessage);
                break;
            case "hotelAvailability":
                adjustHotelAvailability(offer, offerMessage);
                break;
            case "baseFlightPrice":
                adjustBaseTransportPrice(offer, offerMessage);
                break;
            default:
                throw new IllegalStateException("Unexpected attribute: " + attributeToModify);
        }

        logger.info(offerMessage.getLogMessage());
        return offerMessage;
    }

    private void adjustTransportAvailability(Offer offer, OfferMessage offerMessage) {
        int adjustment = ThreadLocalRandom.current().nextInt(-10, 10);
        setOfferMessageDetails(offerMessage, offer.getTransportID(), "transport_offer_exchange", "transport.availability", adjustment);
        offerMessage.setLogMessage("Modified flight availability for flight " + offer.getTransportID() + " by " + adjustment);
    }

    private void adjustHotelAvailability(Offer offer, OfferMessage offerMessage) {
        int adjustment = ThreadLocalRandom.current().nextInt(-5, 5);
        setOfferMessageDetails(offerMessage, offer.getHotelID(), "hotel_offer_exchange", "hotel.availability", adjustment);
        offerMessage.setLogMessage("Modified availability for hotel " + offer.getHotelID() + " by " + adjustment);
    }

    private void adjustBaseTransportPrice(Offer offer, OfferMessage offerMessage) {
        int adjustment = ThreadLocalRandom.current().nextInt(-100, 100);
        setOfferMessageDetails(offerMessage, offer.getTransportID(), "transport_offer_exchange", "transport.price", adjustment);
        offerMessage.setLogMessage("Modified base price for flight " + offer.getTransportID() + " by " + adjustment);
    }

    private void setOfferMessageDetails(OfferMessage offerMessage, long id, String exchange, String messageKey, int value) {
        offerMessage.setID(id);
        offerMessage.setValue(value);
        offerMessage.setMessageType(messageKey);
        offerMessage.setExchange(exchange);
    }

    private ChangeMessage createChangeMessage (Offer offer, ChangeMessage changeMessage) {
        changeMessage.setOfferId(offer.getId());
        changeMessage.setHotelId(offer.getHotelID());
        changeMessage.setTransportId(offer.getTransportID());
        changeMessage.setContent("change");
        return changeMessage;
    }

    private void logAndSaveOfferChanges(Offer offer, OfferMessage offerMessage, String attributeToModify) {
        OfferChange database_entry = new OfferChange(offer.getId(), offerMessage.getLogMessage(), attributeToModify);
        offerChangeRepository.save(database_entry);
    }

    private void sendMessage(OfferMessage offerMessage) {
        rabbitTemplate.convertSendAndReceive(offerMessage.getExchange(), offerMessage.getMessageType(), offerMessage);
    }
    private void sendSocketMessage(ChangeMessage changeMessage) {
        rabbitTemplate.convertSendAndReceive("changes_exchange", "changes.#", changeMessage);
    }
=======
>>>>>>> origin/main

}
