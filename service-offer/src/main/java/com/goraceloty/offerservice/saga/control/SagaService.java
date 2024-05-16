package com.goraceloty.offerservice.saga.control;

import java.util.Random;

import com.goraceloty.offerservice.offer.control.OfferService;
import com.goraceloty.offerservice.offer.entity.Offer;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.goraceloty.offerservice.saga.entity.SagaMessage;
@AllArgsConstructor
@Service
public class SagaService {

    private final OfferService offerService;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @RabbitListener(queues = "offer_queue")
    public void receiveMessage(String message) {
        processOfferMessage(message);
    }

    private void processOfferMessage(String message) {
        // process the message
    }
}