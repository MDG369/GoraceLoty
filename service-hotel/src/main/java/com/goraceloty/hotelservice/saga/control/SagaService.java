package com.goraceloty.hotelservice.saga.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goraceloty.hotelservice.hotel.control.HotelService;
import com.goraceloty.hotelservice.saga.entity.ErrorMessage;
import com.goraceloty.hotelservice.saga.entity.ErrorType;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
@Log
public class SagaService {

    private final RabbitTemplate rabbitTemplate;
    private final HotelService hotelService;
    private final ObjectMapper objectMapper;
    private final Set<UUID> compensatedRequests = ConcurrentHashMap.newKeySet();


    @RabbitListener(queues = "hotel_action_queue")
    public String handleAction(ReservationRequest message) throws JsonProcessingException {
        processHotelBooking(message);
        return "Done";
    }

    public void processHotelBooking(ReservationRequest message) {
        try {
            log.info("Received message: + " + this.objectMapper.writeValueAsString(message));
            if (!compensatedRequests.contains(message.getReservationRequestID())) {
                log.info("test");
                hotelService.bookHotelRooms(message.getHotelID(), message.getNumOfSingleRooms(),
                        message.getNumOfDoubleRooms(), message.getNumOfTripleRooms(),
                        message.getNumOfStudios(), message.getNumOfApartments(), message.getStartDate(), message.getNumOfDays());
            }
        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            log.info("Error: " + e.getMessage());
            this.compensatedRequests.add(message.getReservationRequestID());
            sendErrorMessage(message);
        }
    }

    @RabbitListener(queues = "hotel_compensation_queue")
    private void handleCompensation(ReservationRequest message) throws JsonProcessingException {
        log.info("Got compensation message: " + this.objectMapper.writeValueAsString(message));
        if (!compensatedRequests.contains(message.getReservationRequestID())) {
            log.info("compensating...");
            hotelService.cancelBookingHotelRooms(message.getHotelID(), message.getNumOfSingleRooms(),
                    message.getNumOfDoubleRooms(), message.getNumOfTripleRooms(),
                    message.getNumOfStudios(), message.getNumOfApartments(), message.getStartDate(), message.getNumOfDays());
        }

    }

    private void sendErrorMessage(ReservationRequest message) {
        rabbitTemplate.convertAndSend("error_exchange", "error.baz", new ErrorMessage(message, ErrorType.HOTEL));
    }
}
