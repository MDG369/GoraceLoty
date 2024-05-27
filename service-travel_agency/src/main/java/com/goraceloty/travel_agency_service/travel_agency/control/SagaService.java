package com.goraceloty.travel_agency_service.travel_agency.control;

import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.saga.entity.ErrorMessage;
import com.goraceloty.hotelservice.saga.entity.ErrorType;
import com.goraceloty.hotelservice.saga.entity.SagaHotelBookingMessage;
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
    private final TravelAgencyService travelAgencyService;

    @RabbitListener(queues = "reservation_action_queue")
    public void handleAction(SagaHotelBookingMessage message) {
        processHotelBooking(message);
    }

    public void processHotelBooking(SagaHotelBookingMessage message) {
        try {


        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            sendErrorMessage(message);
        }
    }

    @RabbitListener(queues = "reservation_compensation_queue")
    private void handleCompensation(SagaHotelBookingMessage message) {

    }

    private void sendErrorMessage(SagaHotelBookingMessage message) {
        rabbitTemplate.convertAndSend("error_exchange", "error_queue", new ErrorMessage(message.getReservationId(), ErrorType.HOTEL));
    }
}
