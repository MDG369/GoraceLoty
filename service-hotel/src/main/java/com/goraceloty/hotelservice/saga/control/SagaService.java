package com.goraceloty.hotelservice.saga.control;

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
    private final HotelService hotelService;

    @RabbitListener(queues = "hotel_queue")
    public void receiveMessage(SagaHotelBookingMessage message) {
        processHotelBooking(message);
    }

    public void processHotelBooking(SagaHotelBookingMessage message) {
        try {
            hotelService.bookHotelRooms(message.getHotelId(), message.getNumOfBookedSingleRooms(),
                    message.getNumOfBookedDoubleRooms(), message.getNumOfBookedTripleRooms(),
                    message.getNumOfBookedStudios(), message.getNumOfBookedApartments(), message.getDates());

        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            sendErrorMessage(message);
        }
    }

    private void cancelHotelBooking(SagaHotelBookingMessage message) {
        hotelService.cancelBookingHotelRooms(message.getHotelId(), message.getNumOfBookedSingleRooms(),
                message.getNumOfBookedDoubleRooms(), message.getNumOfBookedTripleRooms(),
                message.getNumOfBookedStudios(), message.getNumOfBookedApartments(), message.getDates());
    }

    private void sendErrorMessage(SagaHotelBookingMessage message) {
        rabbitTemplate.convertAndSend("error_exchange", "error_queue", new ErrorMessage(message.getReservationId(), ErrorType.HOTEL));
    }
}
