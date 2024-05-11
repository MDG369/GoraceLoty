package com.goraceloty.hotelservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Exchange sagaExchange() {
        return ExchangeBuilder.directExchange("saga-exchange").build();
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    // QUEUES
    @Bean
    public Queue api1ProducerQueue() {
        return QueueBuilder.durable("api1-producer-queue").build();
    }

    @Bean
    public Queue api1ConsumerQueue() {
        return QueueBuilder.durable("api1-consumer-queue").build();
    }

    @Bean
    public Queue api2ProducerQueue() {
        return QueueBuilder.durable("api2-producer-queue").build();
    }

    @Bean
    public Queue api2ConsumerQueue() {
        return QueueBuilder.durable("api2-consumer-queue").build();
    }

    @Bean
    public Queue api3ProducerQueue() {
        return QueueBuilder.durable("api3-producer-queue").build();
    }

    @Bean
    public Queue api3ConsumerQueue() {
        return QueueBuilder.durable("api3-consumer-queue").build();
    }



    // BINDING
    @Bean
    public Binding api1ConsumerBinding() {
        return BindingBuilder.bind(api1ConsumerQueue()).to(sagaExchange()).with("api1-consumer-routing-key").noargs();
    }

    @Bean
    public Binding api1ProducerBinding() {
        return BindingBuilder.bind(api1ProducerQueue()).to(sagaExchange()).with("api1-producer-routing-key").noargs();
    }

    @Bean
    public Binding api2ConsumerBinding() {
        return BindingBuilder.bind(api2ConsumerQueue()).to(sagaExchange()).with("api2-consumer-routing-key").noargs();
    }

    @Bean
    public Binding api2ProducerBinding() {
        return BindingBuilder.bind(api2ProducerQueue()).to(sagaExchange()).with("api2-producer-routing-key").noargs();
    }

    @Bean
    public Binding api3ConsumerBinding() {
        return BindingBuilder.bind(api3ConsumerQueue()).to(sagaExchange()).with("api3-consumer-routing-key").noargs();
    }

    @Bean
    public Binding api3ProducerBinding() {
        return BindingBuilder.bind(api3ProducerQueue()).to(sagaExchange()).with("api3-producer-routing-key").noargs();
    }


}