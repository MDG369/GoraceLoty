package com.goraceloty.hotelservice.saga.control;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.goraceloty.hotelservice.saga.entity.SagaMessage;
@Service
@AllArgsConstructor
public class SagaService {

    private final RabbitTemplate rabbitTemplate;

    public void invokeApisInSaga() {
        rabbitTemplate.convertAndSend("saga-exchange", "api1-consumer-routing-key", new SagaMessage("API1"));
    }

    @RabbitListener(queues = "api1-producer-queue")
    public void handleApi1Response(SagaMessage message) {
        System.out.println("API1 " + message.getStatus());
        if (message.getStatus().equals("success")) {
            rabbitTemplate.convertAndSend("saga-exchange", "api2-consumer-routing-key",
                    new SagaMessage("API2", message.getOrderId()));
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API1 execution failed");
        }
    }

    @RabbitListener(queues = "api2-producer-queue")
    public void handleApi2Response(SagaMessage message) {
        System.out.println("API2 " + message.getStatus());
        if (message.getStatus().equals("success")) {
            rabbitTemplate.convertAndSend("saga-exchange", "api3-consumer-routing-key",
                    new SagaMessage("API3", message.getOrderId()));
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API2 execution failed compensation initiated");
            rabbitTemplate.convertAndSend("saga-exchange", "compensate-api1-routing-key",
                    new SagaMessage("Fail", message.getOrderId()));
        }
    }

    @RabbitListener(queues = "api3-producer-queue")
    public void handleApi3Response(SagaMessage message) {
        System.out.println("API3 " + message.getStatus());
        if (message.getStatus().equals("success")) {
            System.out.println("ALL API SUCCESSFULLY EXECUTED");
        } else if (message.getStatus().equals("fail")) {
            System.out.println("API3 execution failed compensation initiated");
            rabbitTemplate.convertAndSend("saga-exchange", "compensate-api2-routing-key",
                    new SagaMessage("Fail", message.getOrderId()));
        }
    }
}
