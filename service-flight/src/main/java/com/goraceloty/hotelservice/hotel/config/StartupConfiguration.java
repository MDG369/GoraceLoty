package com.goraceloty.hotelservice.hotel.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.hotelservice.JsonProcessingExample;

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

                var jsonFile = resourceLoader.getResource("file:data/transport_final_clean.json").getInputStream();
                System.out.println("Got path");
                var jsonData = objectMapper.readTree(jsonFile);
                System.out.println("Data loaded");
                JsonProcessingExample.processJsonData(
                        "jdbc:postgresql://localhost:5432/svc_flight",
                        jsonData,"INSERT INTO transports(transportid, type_of_transport, num_total_seats, num_available_seats, num_base_price, city_departure, city_arrival, date_departure, date_arrival) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                                "ON CONFLICT (transportid) DO UPDATE SET " +
                                "type_of_transport = EXCLUDED.type_of_transport, " +
                                "num_total_seats = EXCLUDED.num_total_seats, " +
                                "num_available_seats = EXCLUDED.num_available_seats, " +
                                "num_base_price = EXCLUDED.num_base_price, " +
                                "city_departure = EXCLUDED.city_departure, " +
                                "city_arrival = EXCLUDED.city_arrival, " +
                                "date_departure = EXCLUDED.date_departure, " +
                                "date_arrival = EXCLUDED.date_arrival;",
                        "gl_pg_user",
                        "g0r4c3_l0ty"
                );
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
