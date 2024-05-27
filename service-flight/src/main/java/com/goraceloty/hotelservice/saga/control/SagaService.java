package com.goraceloty.hotelservice.saga.control;

import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.saga.entity.ErrorMessage;
import com.goraceloty.hotelservice.saga.entity.ErrorType;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SagaService
{
    private final RabbitTemplate rabbitTemplate;
    private final TransportService transportService;

    @RabbitListener(queues = "transport_action_queue")
    public void handleAction(ReservationRequest message) {
        processHotelBooking(message);
    }

    public void processHotelBooking(ReservationRequest reservationRequest) {
        try {
            transportService.bookTransport(reservationRequest);

        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            sendErrorMessage(reservationRequest);
        }
    }

    @RabbitListener(queues = "transport_compensation_queue")
    private void handleCompensation(ReservationRequest reservationRequest) {
        transportService.cancelBookingTransport(reservationRequest);
    }

    private void sendErrorMessage(ReservationRequest reservationRequest) {
        rabbitTemplate.convertAndSend("error_exchange", "error_queue", new ErrorMessage(reservationRequest, ErrorType.TRANSPORT));
    }
}
