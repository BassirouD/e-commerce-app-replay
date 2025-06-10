package com.koula.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
//@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    public OrderProducer(KafkaTemplate<String, OrderConfirmation> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {
        //log.info("Sending confirmation order confirmation: {}", orderConfirmation);
        logger.info("Sending confirmation order confirmation: {}", orderConfirmation);

        Message<OrderConfirmation> message = MessageBuilder
                .withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC, "order-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
