package com.goraceloty.hotelservice.hotel.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.goraceloty.hotelservice.hotel.control.TransportService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityChangeListener {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AvailabilityChangeListener.class);

    @Autowired
    private TransportService transportService;
    @RabbitListener(queues = "offersQueue")
    public void handleOffer(String message) {
            logger.info("Received update: {}");
            System.out.println("Received update: " + message);
            transportService.addAvailableSeats(2L, 100);
    }
}
