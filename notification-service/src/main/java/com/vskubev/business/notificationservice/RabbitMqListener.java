package com.vskubev.business.notificationservice;

import com.vskubev.business.notificationservice.configuration.ApplicationConfigReader;
import com.vskubev.business.notificationservice.map.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author v.skubyev
 */
@Slf4j
@EnableRabbit
@Service
public class RabbitMqListener {
    @Autowired
    ApplicationConfigReader applicationConfigReader;

    /**
     * Message listener for app1
     * @param data a user defined object used for deserialization of message
     */
    @RabbitListener(queues = "auth-queue")
    public void receiveMessageForAuth(UserDTO data) {
        log.info("Received message: {} from auth queue.", data);
        try {
            log.info("Making REST call to the API");

            //TODO: Code to make REST call

            log.info("<< Exiting receiveMessageForApp1() after API call.");
        } catch(HttpClientErrorException ex) {
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.info("Delay...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { }
                log.info("Throwing exception so that message will be requed in the queue.");
                // Note: Typically Application specific exception should be thrown below
                throw new RuntimeException();
            } else {
                throw new AmqpRejectAndDontRequeueException(ex);
            }
        } catch(Exception e) {
            log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }


}
