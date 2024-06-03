package com.goraceloty.offersagaorchestrator;

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
    public Long bookOffer(@RequestBody ReservationRequest reservationRequest) {
        return offerPurchaseSaga.bookOffer(reservationRequest);
    }

}
