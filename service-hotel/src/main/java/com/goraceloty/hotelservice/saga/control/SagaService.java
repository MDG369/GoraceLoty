package com.goraceloty.hotelservice.saga.control;

import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.saga.entity.ErrorMessage;
import com.goraceloty.hotelservice.saga.entity.ErrorType;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
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
    private final HotelService hotelService;

    @RabbitListener(queues = "hotel_action_queue")
    public void handleAction(ReservationRequest message) {
        processHotelBooking(message);
    }

    public void processHotelBooking(ReservationRequest message) {
        try {
            hotelService.bookHotelRooms(message.getHotelID(), message.getNumOfSingleRooms(),
                    message.getNumOfDoubleRooms(), message.getNumOfTripleRooms(),
                    message.getNumOfStudios(), message.getNumOfApartments(), message.getStartDate(), message.getNumOfDays());

        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            sendErrorMessage(message);
        }
    }

    @RabbitListener(queues = "hotel_compensation_queue")
    private void handleCompensation(ReservationRequest message) {
        hotelService.cancelBookingHotelRooms(message.getHotelID(), message.getNumOfSingleRooms(),
                message.getNumOfDoubleRooms(), message.getNumOfTripleRooms(),
                message.getNumOfStudios(), message.getNumOfApartments(), message.getStartDate(), message.getNumOfDays());
    }

    private void sendErrorMessage(ReservationRequest message) {
        rabbitTemplate.convertAndSend("error_exchange", "error.queue.baz", new ErrorMessage(message, ErrorType.HOTEL));
    }
}
