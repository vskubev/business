package com.vskubev.business.notificationservice;

import com.vskubev.business.notificationservice.configuration.ApplicationConfigReader;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author v.skubyev
 */
@EnableRabbit
@SpringBootApplication
public class NotificationServiceApplication {
    private final ApplicationConfigReader applicationConfigReader;

    public NotificationServiceApplication(ApplicationConfigReader applicationConfigReader) {
        this.applicationConfigReader = applicationConfigReader;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    /**
     * сonnectionFactory — для соединения с RabbitMQ
     */
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        connectionFactory.setUsername(applicationConfigReader.getAuthUsername());
        connectionFactory.setPassword(applicationConfigReader.getAuthPassword());
        return connectionFactory;
    }

    /**
     * rabbitTemplate — для отправки и приема сообщений (настройки, порт, настройки десериализации итд)
     */
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
