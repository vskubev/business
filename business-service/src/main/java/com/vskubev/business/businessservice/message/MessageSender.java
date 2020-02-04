package com.vskubev.business.businessservice.message;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Message sender to send message to queue using exchange.
 *
 * @author v.skubyev
 */
@Slf4j
@Component
public class MessageSender {
    private final RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send message
     *
     * @param exchange exchange name
     * @param routingKey routing key
     * @param data data
     */
    public <T> void sendMessage(String exchange, String routingKey, T data) {
        log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue.");
    }
}
