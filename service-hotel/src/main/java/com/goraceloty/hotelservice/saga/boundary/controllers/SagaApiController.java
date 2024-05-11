package com.goraceloty.hotelservice.saga.boundary.controllers;
import com.goraceloty.hotelservice.saga.control.SagaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
public class SagaApiController {
    private final SagaService sagaService;

    @GetMapping("/invoke")
    public String invokeApisInSaga() {
        sagaService.invokeApisInSaga();
        return "APIs invoked";
    }
}
