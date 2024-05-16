package com.goraceloty.hotelservice.saga.control;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log
public class SagaService {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "hotel_queue")
    public void receiveMessage(String message) {
        processHotelBooking(message);
    }

    public void processHotelBooking(String message) {
        try {
            log.info(message);
            // Process the message (e.g., book hotel room)
            // If successful, commit the changes
            // Otherwise, throw an exception to trigger compensating transactions
        } catch (Exception e) {
            // Handle errors and exceptions
            // Implement compensating transactions to rollback changes
            cancelHotelBooking();
        }
    }
    private void cancelHotelBooking() {
        // Implement compensating logic to cancel the hotel booking
    }
}
