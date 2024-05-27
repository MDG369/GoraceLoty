package com.goraceloty.offerservice.offer.control;

import com.goraceloty.offerservice.offer.entity.ReservationRequest;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.goraceloty.offerservice.offer.entity.OfferFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                .baseUrl("http://saga-orchestrator:8084")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public String GetTransports(OfferFilter offerFilter) throws IOException {
        URL url;
        System.out.println("GetTransports" + offerFilter.getCity());
        if(offerFilter.getCity() != null) {
            url = new URL("http://service-flight:8082/transports/matching?cityArrival=" + offerFilter.getCity().replaceAll(" ", "%20"));
        }
        else {
            url = new URL("http://service-flight:8082/transports");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("############### code: " + responseCode);
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
        return "[\"transport\" : none]";
    }

    public String GetTransportss(OfferFilter offerFilter) throws IOException, JSONException {
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
    }

    public String BuildOffer(OfferFilter offerFilter) {
        return "OK";
    }

    public Long createOffer(OfferFilter offer) {
        return offerRepository.save(offer).getId();
    }

    public void updateOfferName(Long offerId, String name) {

    }

    public Mono<String> tryBookingOffer(ReservationRequest reservationRequest) {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/booking")
                        .build())
                .body(Mono.just(reservationRequest), ReservationRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Initiating booking saga failed with error: " + e);
                    return Mono.empty();
                });

    }
}
