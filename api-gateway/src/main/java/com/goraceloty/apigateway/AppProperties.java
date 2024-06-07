package com.goraceloty.apigateway;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "service.url")
public class AppProperties {

    private String hotel;
    private String transport;
    private String offer;
    private String travelagency;
    private String sagaorchestrator;
}

