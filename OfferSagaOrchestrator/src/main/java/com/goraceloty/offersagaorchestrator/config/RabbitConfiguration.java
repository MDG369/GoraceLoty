package com.goraceloty.offersagaorchestrator.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost"); // Set RabbitMQ hostname (same as service name in Docker Compose)
        connectionFactory.setPort(5672); // Set RabbitMQ port
        connectionFactory.setUsername("guest"); // Set RabbitMQ username
        connectionFactory.setPassword("guest"); // Set RabbitMQ password
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
}
