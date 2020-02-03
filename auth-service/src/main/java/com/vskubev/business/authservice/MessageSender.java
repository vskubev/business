package com.vskubev.business.authservice;



import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Message sender to send message to queue using exchange.
 * @author v.skubyev
 */
@Slf4j
@Component
public class MessageSender {

    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("rabbitmq");
        connectionFactory.setPassword("rabbitmq");
        return connectionFactory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    /**
     *
     * @param rabbitTemplate
     * @param exchange
     * @param routingKey
     * @param data
     */
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue.");
    }
}
