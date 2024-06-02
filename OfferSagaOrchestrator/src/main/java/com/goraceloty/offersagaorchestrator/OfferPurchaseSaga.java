package com.goraceloty.offersagaorchestrator;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.goraceloty.offersagaorchestrator.entity.ErrorMessage;
import com.goraceloty.offersagaorchestrator.entity.ErrorType;
import com.goraceloty.offersagaorchestrator.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log
@Service
@Component
@AllArgsConstructor
public class OfferPurchaseSaga{

    private final RabbitTemplate rabbitTemplate;
    private final View error;
    private final ObjectMapper objectMapper;
    private final Set<UUID> compensatedRequests = ConcurrentHashMap.newKeySet();


    public Long bookOffer(ReservationRequest reservationRequest) {
        Long reservationId = null;
        try {
            // Returns reservationId for payment in the future
            //
            // SagaHotelBookingMessage sagaHotelBookingMessage = new SagaHotelBookingMessage()
            // Step 1: Send to travelAgency to create reservation with status unpaid

        //  log.info("Sending " + mapper.writeValueAsString(reservationRequest));
            reservationId = (Long) rabbitTemplate.convertSendAndReceive("reservation_exchange", "reservation.action.baz", reservationRequest);

        // Step 2: Reserve hotel
            String res2 = (String) rabbitTemplate.convertSendAndReceive("hotel_exchange", "hotel.action.baz", reservationRequest);

        // Step 3: Reserve flight
            String res3 = (String) rabbitTemplate.convertSendAndReceive("transport_exchange", "transport.action.baz", reservationRequest);

        } catch (Exception e) {
            log.info(e.getMessage());
            // Handle failure, initiate compensation
            // Example: send compensating messages to cancel previous reservations
        }
        return reservationId;
    }
    @RabbitListener(queues = "error_queue")
    public void handleError(ErrorMessage errorMessage) {
        try {
            if (compensatedRequests.contains(errorMessage.getReservationRequest().getReservationRequestID())) {
                return;
            }
            log.info("gotErrorMessage: " + errorMessage.getErrorType() + " " + this.objectMapper.writeValueAsString(errorMessage.getReservationRequest()));
            switch (errorMessage.getErrorType()) {
                case RESERVATION -> {
                    rabbitTemplate.convertAndSend("hotel_exchange", "hotel.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("transport_exchange", "transport.compensation.baz", errorMessage.getReservationRequest());
                }
                case HOTEL -> {
                    rabbitTemplate.convertAndSend("reservation_exchange", "reservation.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("transport_exchange", "transport.compensation.baz", errorMessage.getReservationRequest());
                }
                case TRANSPORT -> {
                    rabbitTemplate.convertAndSend("hotel_exchange", "hotel.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("reservation_exchange", "reservation.compensation.baz", errorMessage.getReservationRequest());
                }
            }
            compensatedRequests.add(errorMessage.getReservationRequest().getReservationRequestID());
        } catch (Exception e){
            log.info("Error while sending compensation: " + e.getMessage());
        }
    }

}
