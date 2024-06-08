package com.goraceloty.apigateway.saga.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goraceloty.apigateway.saga.entity.ChangeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
@AllArgsConstructor
public class SagaService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "changes_queue")
    public String sendChangeMessage(ChangeMessage message) throws JsonProcessingException {
        log.info("Received message: + " + objectMapper.writeValueAsString(message));
        messagingTemplate.convertAndSend("/topic/changes",  message);

        return "Request processed";
    }

}
