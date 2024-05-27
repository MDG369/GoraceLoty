package com.goraceloty.travel_agency_service.saga.control;

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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Log
public class SagaService {

    private final RabbitTemplate rabbitTemplate;
    private final TravelAgencyService travelAgencyService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @RabbitListener(queues = "reservation_action_queue")
    public void handleAction(ReservationRequest reservationRequest) {
        processHotelBooking(reservationRequest);
    }

    public void processHotelBooking(ReservationRequest reservationRequest) {
        CompletableFuture.runAsync(() -> {
            try {
                OfferReservation offerReservation = new OfferReservation();
                offerReservation.createOfferReservationFromReservationRequest(reservationRequest);
                // Save offerReservation to the database
                travelAgencyService.addReservation(offerReservation);

                // Schedule the check for 5 minutes later
                scheduler.schedule(() -> checkPaidStatus(offerReservation.getReservationID(), reservationRequest), 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                // Send error message to Orchestrator to cancel the reservation
                sendErrorMessage(reservationRequest);
            }
        });
    }

    private void checkPaidStatus(Long offerReservationId, ReservationRequest reservationRequest) {
        CompletableFuture.runAsync(() -> {
            try {
                OfferReservation offerReservation = travelAgencyService.getOfferReservationById(offerReservationId)
                        .orElseThrow(() -> new Exception("OfferReservation not found"));
                if (!offerReservation.getIsPaid()) {
                    // Take appropriate action if the reservation is not paid
                    log.info("Reservation with id " + offerReservationId + " is not paid, cancelling the process");
                    handleCompensation(reservationRequest);
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
        OfferReservation offerReservation = new OfferReservation();
        offerReservation.createOfferReservationFromReservationRequest(reservationRequest);
        offerReservation = travelAgencyService.getOfferReservationByExample(offerReservation).getFirst();
        travelAgencyService.removeTransportById(offerReservation.getReservationID());
    }

    private void sendErrorMessage(ReservationRequest reservationRequest) {
        rabbitTemplate.convertAndSend("error_exchange", "error_queue", new ErrorMessage(reservationRequest, ErrorType.RESERVATION));
    }
}
