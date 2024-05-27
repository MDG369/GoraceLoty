package com.goraceloty.hotelservice.hotel.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.hotelservice.JsonProcessingHotels;

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

                var jsonFile = resourceLoader.getResource("file:/app/data/hotels_final_clean.json").getInputStream();
                System.out.println("Got path");
                var jsonData = objectMapper.readTree(jsonFile);
                System.out.println("Data loaded");
                JsonProcessingHotels.processJsonData(
                        "jdbc:postgresql://db_postgres:5432/svc_hotel",
                        jsonData,"INSERT INTO hotels(hotelid, hotel_name, standard, country, city, children_allowed, address ) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                                "ON CONFLICT (hotelid) DO UPDATE SET " +
                                "hotel_name = EXCLUDED.hotel_name, " +
                                "standard = EXCLUDED.standard, " +
                                "country = EXCLUDED.country, " +
                                "city = EXCLUDED.city, " +
                                "children_allowed = EXCLUDED.children_allowed, " +
                                "address = EXCLUDED.address;",
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

