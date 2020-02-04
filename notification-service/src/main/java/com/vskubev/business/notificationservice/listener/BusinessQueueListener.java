package com.vskubev.business.notificationservice.listener;

import com.vskubev.business.notificationservice.message.CreateCategoryMessage;
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
public class BusinessQueueListener {
    @RabbitListener(queues = "business-queue")
    public void receiveMessageForBusiness(CreateCategoryMessage data) {
        log.info("Received message: {} from business queue.", data);

        final String name = data.getName();
        final long ownerId = data.getOwnerId();
    }
}