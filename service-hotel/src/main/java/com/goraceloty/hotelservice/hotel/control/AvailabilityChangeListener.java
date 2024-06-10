package com.goraceloty.hotelservice.hotel.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.hotelservice.hotel.entity.OfferMessageReceiver;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AvailabilityChangeListener {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AvailabilityChangeListener.class);
    private final ObjectMapper objectMapper;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private AvailabilityService availabilityService;

    @RabbitListener(queues = "offersHotelQueue")
    public void handleOffer(OfferMessageReceiver message)  throws JsonProcessingException {
        logger.info("Received update: {}", objectMapper.writeValueAsString(message));
        System.out.println("Received update: " + message);
        ProcessOfferChange(message);
        //transportService.addAvailableSeats(2L, 100);
    }

    private void ProcessOfferChange(OfferMessageReceiver message) {
        availabilityService.addHotelAvailability(message.getID(), message.getValue());
        System.out.println("Updated hotel availability" + message.getID() + "by" + message.getValue());
    }
}