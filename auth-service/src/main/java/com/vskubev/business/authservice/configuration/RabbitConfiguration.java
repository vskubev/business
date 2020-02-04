package com.vskubev.business.authservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author v.skubyev
 */
@ConfigurationProperties(prefix = "rabbit")
@Component
@Getter
@Setter
public class RabbitConfiguration {
    private String authExchangeName;
    private String authRoutingKey;
    private String authUsername;
    private String authPassword;


    /**
     * Bean for rabbitTemplate
     */
    private ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        connectionFactory.setUsername(getAuthUsername());
        connectionFactory.setPassword(getAuthPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
