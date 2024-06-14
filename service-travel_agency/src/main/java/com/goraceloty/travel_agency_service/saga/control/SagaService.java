package com.goraceloty.travel_agency_service.saga.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.travel_agency_service.saga.entity.ReservationRequest;
import com.goraceloty.travel_agency_service.saga.entity.ErrorMessage;
import com.goraceloty.travel_agency_service.saga.entity.ErrorType;
import com.goraceloty.travel_agency_service.travel_agency.control.TravelAgencyService;
import com.goraceloty.travel_agency_service.travel_agency.entity.OfferReservation;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;

@Component
@AllArgsConstructor
@Log
public class SagaService {

    private final RabbitTemplate rabbitTemplate;
    private final TravelAgencyService travelAgencyService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ObjectMapper objectMapper;
    private final Set<UUID> compensatedRequests = ConcurrentHashMap.newKeySet();


    @RabbitListener(queues = "reservation_action_queue")
    public void handleAction(ReservationRequest reservationRequest) throws JsonProcessingException {
        log.info("Received request: " + objectMapper.writeValueAsString(reservationRequest) );
        processHotelBooking(reservationRequest);
    }

    public Long processHotelBooking(ReservationRequest reservationRequest) {
        OfferReservation offerReservation = new OfferReservation();
        offerReservation.createOfferReservationFromReservationRequest(reservationRequest);
        // Save offerReservation to the database
        if (compensatedRequests.contains(reservationRequest.getReservationRequestID())) {
            log.info("Request already compensating, not creating the reservation " + reservationRequest.getReservationRequestID() );
            return null;
        }
        offerReservation = travelAgencyService.addReservation(offerReservation);
        OfferReservation finalOfferReservation = offerReservation;
        CompletableFuture.runAsync(() -> {
            try {
                // Schedule the check for 5 minutes later
                scheduler.schedule(() -> checkPaidStatus(finalOfferReservation.getReservationID(), reservationRequest), 2, TimeUnit.MINUTES);
            } catch (Exception e) {
                // Send error message to Orchestrator to cancel the reservation
                sendErrorMessage(reservationRequest);
            }

        });
        return offerReservation.getReservationID();
    }

    private void checkPaidStatus(Long offerReservationId, ReservationRequest reservationRequest) {
        CompletableFuture.runAsync(() -> {
            try {
                OfferReservation offerReservation = travelAgencyService.getOfferReservationById(offerReservationId)
                        .orElseThrow(() -> new Exception("OfferReservation not found"));
                if (!offerReservation.getIsPaid()) {
                    // Take appropriate action if the reservation is not paid
                    if (!compensatedRequests.contains(reservationRequest.getReservationRequestID())) {
                        log.info("Reservation with id " + offerReservationId + " is not paid, cancelling the process");
                        travelAgencyService.removeReservationById(offerReservationId);
                        compensatedRequests.add(reservationRequest.getReservationRequestID());
                    }
                    sendErrorMessage(reservationRequest);
                }
            } catch (Exception e) {
                log.severe("Error checking paid status: " + e.getMessage());
                sendErrorMessage(reservationRequest);
            }
        });
    }


    @RabbitListener(queues = "reservation_compensation_queue")
    private void handleCompensation(ReservationRequest reservationRequest) {
        if (!compensatedRequests.contains(reservationRequest.getReservationRequestID())) {
            try {
                travelAgencyService.removeReservationById(travelAgencyService.getOfferReservationByReservationRequest(reservationRequest).getReservationID());
                compensatedRequests.add(reservationRequest.getReservationRequestID());
                log.info("Reservation with id " + reservationRequest.getReservationRequestID() + " is compensated");
            }
            catch (Exception e) {
                log.info("Exception while compensating: " + e.getMessage());
            }
        }
    }

    private void sendErrorMessage(ReservationRequest reservationRequest) {
        rabbitTemplate.convertAndSend("error_exchange", "error.baz", new ErrorMessage(reservationRequest, ErrorType.RESERVATION));
    }
}
