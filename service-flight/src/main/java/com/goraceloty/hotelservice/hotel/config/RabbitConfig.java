package com.goraceloty.hotelservice.hotel.config;


import com.goraceloty.hotelservice.hotel.control.TransportService;
import com.goraceloty.hotelservice.saga.control.SagaService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String topicExchangeName = "transport_exchange";
    static final String offerExchangeName = "offer_exchange";

    static final String actionQueueName = "transport_action_queue";
    static final String compensationQueueName = "transport_compensation_queue";
    static final String offerQueue = "offersQueue";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
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
    TopicExchange actionExchange() {
        return new TopicExchange(offerExchangeName);
    }

    @Bean
    Binding actionBinding(Queue actionQueue, TopicExchange exchange) {
        return BindingBuilder.bind(actionQueue).to(exchange).with("transport.action.#");
    }

    @Bean
    Binding compensationBinding(Queue compensationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(compensationQueue).to(exchange).with("transport.compensation.#");
    }

    @Bean
    Binding offersBinding(Queue offersQueue, TopicExchange exchange) {
        return BindingBuilder.bind(offersQueue).to(exchange).with("offers.#");
    }

    @Bean
    MessageListenerAdapter actionListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleAction");
    }

//    @Bean
//    public MessageListenerAdapter offersListenerAdapter(OfferService offerService) {
//        return new MessageListenerAdapter(offerService, "handleOffer");
//    }

    @Bean
    MessageListenerAdapter compensationListenerAdapter(SagaService sagaService) {
        return new MessageListenerAdapter(sagaService, "handleCompensation");
    }

    @Bean
    MessageListenerAdapter offersListenerAdapter(AvailabilityChangeListener availabilityChangeListener) {
        return new MessageListenerAdapter(availabilityChangeListener, "handleOffer");
    }

    @Bean
    SimpleMessageListenerContainer offersContainer(ConnectionFactory connectionFactory,
                                                   MessageListenerAdapter offersListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("offersQueue");
        container.setMessageListener(offersListenerAdapter);
        return container;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message sent successfully");
            } else {
                System.out.println("Message sending failed due to " + cause);
            }
        });
        return rabbitTemplate;
    }
}
