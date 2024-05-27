package com.goraceloty.travel_agency_service.travel_agency.config;


import com.goraceloty.travel_agency_service.saga.control.SagaService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String topicExchangeName = "reservation_exchange";

    static final String actionQueueName = "reservation_action_queue";
    static final String compensationQueueName = "reservation_compensation_queue";

    @Bean
    Queue actionQueue() {
        return new Queue(actionQueueName, false);
    }

    @Bean
    Queue compensationQueue() {
        return new Queue(compensationQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding actionBinding(Queue actionQueue, TopicExchange exchange) {
        return BindingBuilder.bind(actionQueue).to(exchange).with("reservation.creation.#");
    }

    @Bean
    Binding compensationBinding(Queue compensationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(compensationQueue).to(exchange).with("reservation.compensation.#");
    }

    @Bean
    SimpleMessageListenerContainer actionContainer(ConnectionFactory connectionFactory,
                                                   MessageListenerAdapter actionListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(actionQueueName);
        container.setMessageListener(actionListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer compensationContainer(ConnectionFactory connectionFactory,
                                                         MessageListenerAdapter compensationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(compensationQueueName);
        container.setMessageListener(compensationListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter actionListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleAction");
    }

    @Bean
    MessageListenerAdapter compensationListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleCompensation");
    }
}