package com.goraceloty.hotelservice.hotel.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.hotel.entity.OfferMessageReceiver;
import com.goraceloty.hotelservice.hotel.entity.Transport;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AvailabilityChangeListener {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AvailabilityChangeListener.class);
    private final ObjectMapper objectMapper;
    private final Transport transport = new Transport();

    @Autowired
    private TransportService transportService;
    @RabbitListener(queues = "offersTransportQueue")
    public void handleOffer(OfferMessageReceiver message)  throws JsonProcessingException {
            logger.info("Received update: {}", objectMapper.writeValueAsString(message));
            System.out.println("Received update: " + message);
            processOfferChange(message);
            //transportService.addAvailableSeats(2L, 100);
    }

    private void processOfferChange(OfferMessageReceiver message) {
        if ("transport.availability".equals(message.getMessageType())) {
            transportService.addAvailableSeats(message.getID(), (int) message.getValue());
        } else if ("transport.price".equals(message.getMessageType())) {
            transportService.adjustPrice(message.getID(), message.getValue());
        }

        System.out.println("Updated flight " + message.getID() + " by " + message.getValue());
    }
    
    private void checkAndAdjustSeats(Long flightId) {
        int numAvailableSeats = transportService.getAvailableSeats(flightId);
        int numTotalSeats = transportService.getNumTotalSeats(flightId);

        // Check if total seats are less than available seats and adjust if necessary
        if (numTotalSeats < numAvailableSeats) {
            transportService.setTotalSeats(flightId, numAvailableSeats);
            System.out.println("Adjusted total seats to match available seats for flight " + flightId);
        }

        // Check if total seats are less than 0 and adjust if necessary
        if (numTotalSeats < 0) {
            transportService.setTotalSeats(flightId, 0);
            System.out.println("Adjusted total seats to 0 for flight " + flightId);
        }
    }

    public Transport getTransport() {
        return transport;
    }
}
