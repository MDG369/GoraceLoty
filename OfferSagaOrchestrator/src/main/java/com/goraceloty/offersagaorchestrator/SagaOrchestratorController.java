package com.goraceloty.offersagaorchestrator;

import com.goraceloty.offersagaorchestrator.entity.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/booking")
@RestController
public class SagaOrchestratorController {
    private final OfferPurchaseSaga offerPurchaseSaga;
    @GetMapping
    public String bookOffer(ReservationRequest reservationRequest) {
        offerPurchaseSaga.bookOffer(reservationRequest); return "Booked!";
    }

}
