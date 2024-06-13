package com.goraceloty.apigateway.offers.control;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.offers.entity.Offer;
import com.goraceloty.apigateway.offers.entity.OfferChange;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log
@Service
public class OfferClient {

    WebClient webClient;
    AppProperties appProperties;

    public OfferClient(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = webClientBuilder
                .baseUrl(appProperties.getOffer())
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public Mono<Offer[]> getOffersByExample(Offer offer) {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/matching")
                        .build())
                .body(Mono.just(offer), Offer.class)
                .retrieve()
                .bodyToMono(Offer[].class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of offers by example failed with: " + e);
                    return Mono.empty();
                });
    }

    public Mono<OfferChange[]> getOffersChangesByExample() {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/changes")
                        .build())
                .retrieve()
                .bodyToMono(OfferChange[].class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of offer changes by example failed with: " + e);
                    return Mono.empty();
                });
    }

    //public Mono<Offer> getOffersById(Long id) {
    //    return webClient.method(HttpMethod.GET)
    //            .uri(uriBuilder -> uriBuilder
    //                    .path("/matching")
    //                    .queryParam("id", id)
    //                    .build())
    //            .retrieve()
    //            .bodyToMono(Offer[].class)
    //            .flatMap(offers -> offers.length > 0 ? Mono.just(offers[0]) : Mono.empty())
    //            .onErrorResume(RuntimeException.class, e -> {
    //                log.fine("Get offers by id failed: " + e);
    //                return Mono.empty();
    //            });
    //}

}
