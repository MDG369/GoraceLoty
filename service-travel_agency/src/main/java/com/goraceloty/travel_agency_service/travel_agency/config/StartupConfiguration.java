package com.goraceloty.travel_agency_service.travel_agency.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.travel_agency_service.travel_agency.JsonProcessingExample;

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

                var jsonFile = resourceLoader.getResource("file:..\\data\\offer_reservation_data.json").getInputStream();
                System.out.println("Got path");
                var jsonData = objectMapper.readTree(jsonFile);
                System.out.println("Data loaded");
                JsonProcessingExample.processJsonData(
                        "jdbc:postgresql://localhost:5432/svc_travel_agency",
                        jsonData,
                        "INSERT INTO offer_reservation (reservationid, date_start, num_adult, num_children, num_of_days, reservation_time, adjusted_price, offerid, transportid, hotelid) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (reservationid) DO UPDATE SET " +
                                "start_date = EXCLUDED.date_start, num_adult = EXCLUDED.num_adult, num_children = EXCLUDED.num_children, num_of_days = EXCLUDED.num_of_days, " +
                                "reservation_time = EXCLUDED.reservation_time, adjusted_price = EXCLUDED.adjusted_price, offerid = EXCLUDED.offerid, transportid = EXCLUDED.transportid, hotelid = EXCLUDED.hotelid;",
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
