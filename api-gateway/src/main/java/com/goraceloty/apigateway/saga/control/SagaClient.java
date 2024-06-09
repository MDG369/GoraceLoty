package com.goraceloty.apigateway.saga.control;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import com.goraceloty.apigateway.saga.entity.ReservationRequest;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log
@Service
public class SagaClient {
    WebClient webClient;
    AppProperties appProperties;

    public SagaClient(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = webClientBuilder
                .baseUrl(appProperties.getSagaorchestrator())
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public Mono<Long> makeBooking(ReservationRequest request) {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/booking")
                        .build())
                .body(Mono.just(request), ReservationRequest.class)
                .retrieve()
                .bodyToMono(Long.class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of hotels by example failed with: " + e);
                    return Mono.empty();
                });
    }
}
