package com.goraceloty.hotelservice.config;


import com.goraceloty.hotelservice.hotel.control.AvailabilityChangeListener;
import com.goraceloty.hotelservice.saga.control.SagaService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String topicExchangeName = "hotel_exchange";
    static final String offerExchangeName = "hotel_offer_exchange";

    static final String actionQueueName = "hotel_action_queue";
    static final String compensationQueueName = "hotel_compensation_queue";
    static final String offerQueue = "offersHotelQueue";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(customObjectMapper());
    }

    @Bean
    public ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules(); // Registers all available modules including JSR310

        // Configure other settings if needed
        return objectMapper;
    }

    @Bean
    Queue actionQueue() {
        return new Queue(actionQueueName, false);
    }

    @Bean
    Queue compensationQueue() {
        return new Queue(compensationQueueName, false);
    }

    @Bean
    Queue offersQueue() {
        return new Queue(offerQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    TopicExchange offerExchange() {
        return new TopicExchange(offerExchangeName);
    }

    @Bean
    Binding actionBinding(Queue actionQueue, TopicExchange exchange) {
        return BindingBuilder.bind(actionQueue).to(exchange).with("hotel.action.#");
    }

    @Bean
    Binding compensationBinding(Queue compensationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(compensationQueue).to(exchange).with("hotel.compensation.#");
    }

    @Bean
    Binding offersBinding(Queue offersQueue, TopicExchange offerExchange) {
        return BindingBuilder.bind(offersQueue).to(offerExchange).with("hotel.availability");
    }

    @Bean
    MessageListenerAdapter actionListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleAction");
    }

    @Bean
    MessageListenerAdapter compensationListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleCompensation");
    }

//    @Bean
//    MessageListenerAdapter offersListenerAdapter(AvailabilityChangeListener availabilityChangeListener) {
//        return new MessageListenerAdapter(availabilityChangeListener, "handleOffer");
//    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
