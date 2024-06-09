package com.goraceloty.apigateway.transport.control;

import com.goraceloty.apigateway.AppProperties;
import com.goraceloty.apigateway.hotels.entity.Hotel;
import com.goraceloty.apigateway.transport.entity.Transport;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;


@Log
@Service
public class TransportClient {

    WebClient webClient;
    AppProperties appProperties;

    public TransportClient(WebClient.Builder webClientBuilder, AppProperties appProperties) {
        this.appProperties = appProperties;
        this.webClient = webClientBuilder
                .baseUrl(appProperties.getTransport())
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
                .build();
    }

    public Mono<Transport[]> getTransportByExample(Transport transport) {
        return webClient.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                        .path("/matching")
                        .build())
                .body(Mono.just(transport), Hotel.class)
                .retrieve()
                .bodyToMono(Transport[].class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of transport by example failed with: " + e);
                    return Mono.empty();
                });
    }

    public Mono<Transport[]> getAllTransport() {
        return webClient.get().uri(UriBuilder::build)
                .retrieve()
                .bodyToMono(Transport[].class)
                .onErrorResume(RuntimeException.class, e -> {
                    log.fine("Getting list of transport failed with: " + e);
                    return Mono.empty();
                });
    }

}
