package com.goraceloty.offerservice.offer.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.offerservice.JsonProcessingOffers;


@Configuration
public class StartupConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public StartupConfiguration(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Runnable processJsonDataOnStartup() {
        System.out.println("StartedInserting");
        return () -> {
            try {

                var jsonFile = resourceLoader.getResource("file:/app/data/offers.json").getInputStream();
                System.out.println("Got path");
                var jsonData = objectMapper.readTree(jsonFile);
                System.out.println("Data loaded");
                JsonProcessingOffers.processJsonData(
                        "jdbc:postgresql://db_postgres:5432/svc_offer",
                        jsonData,"INSERT INTO offers(id, transportID, hotelID, city_arrival, city_departure, date_start, date_end ) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                                "ON CONFLICT (id) DO UPDATE SET " +
                                "transportID = EXCLUDED.transportID, " +
                                "hotelID = EXCLUDED.hotelID, " +
                                "city_arrival = EXCLUDED.city_arrival, " +
                                "city_departure = EXCLUDED.city_departure, " +
                                "date_start = EXCLUDED.date_start, " +
                                "date_end = EXCLUDED.date_end;",
                        "gl_pg_user",
                        "g0r4c3_l0ty");
                System.out.println("Data loaded full");
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
            }
        };
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        processJsonDataOnStartup().run();
    }
}
