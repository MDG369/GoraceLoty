package com.goraceloty.apigateway.websockets;

import lombok.extern.java.Log;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
@Log
@Controller
public class WebsocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greet(String message) {
        log.info("Got msg: " + message);
        return "Hello, " + message + "!";
    }
}