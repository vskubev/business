package com.vskubev.business.notificationservice;

import com.vskubev.business.notificationservice.message.SuccessfulRegistrationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author v.skubyev
 */
@Component
@EnableRabbit
@Slf4j
public class AuthQueueListener {
    /**
     * Message listener
     *
     * @param data a user defined object used for deserialization of message
     */
    @RabbitListener(queues = "auth-queue")
    public void receiveMessageForAuth(SuccessfulRegistrationMessage data) {
        log.info("Received message: {} from auth queue.", data);

        final String email = data.getEmail();
        final String subject = "Hello from site.org";
        final String message = String.format("Welcome dear %s. Blablabla...", data.getName());

        /*
         * Send email to the end user
         */
    }
}
