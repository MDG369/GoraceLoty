package com.goraceloty.apigateway.travel_agency.control;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.offers.entity.Offer;
import com.goraceloty.apigateway.travel_agency.entity.OfferReservation;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log
@Service
public class TravelAgencyClient {
    WebClient webClient;
    AppProperties appProperties;

    public TravelAgencyClient(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = webClientBuilder
                .baseUrl(appProperties.getTravelagency())
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public Mono<OfferReservation[]> getAllReservations() {
        return webClient.get().uri(uriBuilder -> uriBuilder
                    .path("/matching")
                    .build())
                .retrieve()
                .bodyToMono(OfferReservation[].class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of offer reservations failed with: " + e);
                    return Mono.empty();
                });
    }

}
