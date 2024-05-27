package com.goraceloty.offersagaorchestrator;

import com.goraceloty.offersagaorchestrator.entity.ErrorMessage;
import com.goraceloty.offersagaorchestrator.entity.ReservationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Component
public class OfferPurchaseSaga{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void bookOffer(ReservationRequest reservationRequest) {
        try {
            UUID transactionId = UUID.randomUUID();
            reservationRequest.setId(transactionId);
            //
            // SagaHotelBookingMessage sagaHotelBookingMessage = new SagaHotelBookingMessage()
            // Step 1: Send to travelAgency to create reservation with status unpaid
            rabbitTemplate.convertAndSend("reservation_exchange", "reservation.creation.baz", reservationRequest);

            // Step 2: Reserve hotel
            rabbitTemplate.convertAndSend("hotel_exchange", "hotel.action.baz", reservationRequest);

            // Step 3: Reserve flight
            rabbitTemplate.convertAndSend("flight_exchange", "flight.action.baz", reservationRequest);

        } catch (Exception e) {
            // Handle failure, initiate compensation
            // Example: send compensating messages to cancel previous reservations
        }
    }
    public void handleError(ErrorMessage errorMessage) {
        try {
            switch (errorMessage.getErrorType()) {
                case RESERVATION -> {
                    rabbitTemplate.convertAndSend("hotel_exchange", "hotel.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("flight_exchange", "flight.compensation.baz", errorMessage.getReservationRequest());
                }
                case HOTEL -> {
                    rabbitTemplate.convertAndSend("reservation_exchange", "reservation.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("flight_exchange", "flight.compensation.baz", errorMessage.getReservationRequest());
                }
                case TRANSPORT -> {
                    rabbitTemplate.convertAndSend("hotel_exchange", "hotel.compensation.baz", errorMessage.getReservationRequest());
                    rabbitTemplate.convertAndSend("reservation_exchange", "reservation.compensation.baz", errorMessage.getReservationRequest());
                }
            }
        } catch (Exception e){
            //
        }
    }

    public void getHotelsError() {

    }
}
