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

    @RabbitListener(queues = "api1-consumer-queue")
    public void handleApi1(SagaMessage message) {
        try {
            Long orderId = offerService.createOffer(new Offer(1L, "S"));
            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key",
                    new SagaMessage("success", orderId));
        } catch (Exception e) {
            rabbitTemplate.convertAndSend("saga-exchange", "api1-producer-routing-key", new SagaMessage("fail"));
            return;
        }
    }

//    @RabbitListener(queues = "api2-consumer-queue")
//    public void handleApi2(SagaMessage message) {
//        Long orderId = message.getOrderId();
//        Random random = new Random();
//        String[] arr = new String[] { "Chicken", "Noodles", "Idli", "Rice" };
//        try {
//            Thread.sleep(5000);
//            foodService.createFoodOrder(orderId, arr[random.nextInt(4)]);
//            rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
//                    new SagaMessage("success", orderId));
//        } catch (Exception e) {
//            rabbitTemplate.convertAndSend("saga-exchange", "api2-producer-routing-key",
//                    new SagaMessage("fail", orderId));
//            return;
//        }
//    }
//
//    @RabbitListener(queues = "api3-consumer-queue")
//    public void handleApi3(SagaMessage message) {
//        Long orderId = message.getOrderId();
//        try {
//            deliveryService.createDeliveryOrder(orderId);
//            rabbitTemplate.convertAndSend("saga-exchange", "api3-producer-routing-key", new SagaMessage("success"));
//            foodService.updateOrderStatus(orderId, "Order Placed");
//            deliveryService.updateOrderStatus(orderId, "Order Placed");
//            orderService.updateOrderStatus(orderId, "Order Placed");
//        } catch (Exception e) {
//            rabbitTemplate.convertAndSend("saga-exchange", "api3-producer-routing-key",
//                    new SagaMessage("fail", orderId));
//            return;
//        }
//    }

    @RabbitListener(queues = "compensate-api1-queue")
    public void handleCompensateApi1Request(SagaMessage message) {
        try {
            offerService.updateOfferName(message.getOfferId(), "CANCELLED");
        } catch (Exception e) {
            System.out.println("API1 Compensation Failed");
        }
    }

//    @RabbitListener(queues = "compensate-api2-queue")
//    public void handleCompensateApi2Request(SagaMessage message) {
//        try {
//            foodService.updateOrderStatus(message.getOrderId(), "CANCELLED");
//        } catch (Exception e) {
//            System.out.println("API2 Compensation Failed");
//        }
//        handleCompensateApi1Request(message);
//    }
}