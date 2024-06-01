package com.goraceloty.hotelservice.saga.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.saga.entity.ErrorMessage;
import com.goraceloty.hotelservice.saga.entity.ErrorType;
import com.goraceloty.hotelservice.saga.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

@Log
@Service
@AllArgsConstructor
public class SagaService
{
    private final RabbitTemplate rabbitTemplate;
    private final TransportService transportService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ObjectMapper objectMapper;
    private final Set<UUID> compensatedRequests = ConcurrentHashMap.newKeySet();

    @RabbitListener(queues = "transport_action_queue")
    public String handleAction(ReservationRequest message) throws JsonProcessingException {
        log.info("Received message: + " + objectMapper.writeValueAsString(message));
        processHotelBooking(message);
        return "Request processed";
    }

    public void processHotelBooking(ReservationRequest reservationRequest) {
        try {
            if (!compensatedRequests.contains(reservationRequest.getReservationRequestID())) {
                transportService.bookTransport(reservationRequest);
            }
        } catch (Exception e) {
            // Send error message to Orchestrator in order to cancel the reservation
            compensatedRequests.add(reservationRequest.getReservationRequestID());
            sendErrorMessage(reservationRequest);
        }
    }

    @RabbitListener(queues = "transport_compensation_queue")
    private void handleCompensation(ReservationRequest reservationRequest) throws JsonProcessingException {
        log.info("Got compensation request: " + this.objectMapper.writeValueAsString(reservationRequest));
        if (!compensatedRequests.contains(reservationRequest.getReservationRequestID())) {
            log.info("compensating...");
            transportService.cancelBookingTransport(reservationRequest);
        }
    }

    private void sendErrorMessage(ReservationRequest reservationRequest) {
        rabbitTemplate.convertAndSend("error_exchange", "error.baz", new ErrorMessage(reservationRequest, ErrorType.TRANSPORT));
    }
}
