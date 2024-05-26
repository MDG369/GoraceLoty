package com.goraceloty.offersagaorchestrator;

import com.goraceloty.offersagaorchestrator.entity.ReservationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferPurchaseSaga{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void execute(ReservationRequest reservationRequest) {
        try {

            //

            // Step 1: Reserve hotel
            rabbitTemplate.convertAndSend("hotel_exchange", "foo.bar.baz", reservationRequest.getOffer().getHotelId());

//            // Step 2: Reserve flight
//            rabbitTemplate.convertAndSend("flight_exchange", "reserve_queue", "ReserveFlight");
//
//            // Step 3: Finalize purchase
//            rabbitTemplate.convertAndSend("offer_exchange", "finalize_queue", "FinalizePurchase");
        } catch (Exception e) {
            // Handle failure, initiate compensation
            // Example: send compensating messages to cancel previous reservations
        }
    }

    public void getHotelsError() {

    }
}
