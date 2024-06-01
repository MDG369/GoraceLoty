package com.goraceloty.offersagaorchestrator;

import com.goraceloty.offersagaorchestrator.entity.ReservationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/booking")
@RestController
public class SagaOrchestratorController {
    private final OfferPurchaseSaga offerPurchaseSaga;
    @GetMapping
    public String bookOffer(@RequestBody ReservationRequest reservationRequest) {
        offerPurchaseSaga.bookOffer(reservationRequest); return "Booked!";
    }

}
