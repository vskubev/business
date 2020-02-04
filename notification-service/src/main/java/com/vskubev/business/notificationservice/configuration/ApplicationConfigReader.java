package com.vskubev.business.notificationservice.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author v.skubyev
 */
@Component
@ConfigurationProperties("queue")
@Data
@NoArgsConstructor
public class ApplicationConfigReader {
    private String authExchangeName;
    private String authQueueName;
    private String authRoutingKey;
    private String authUsername;
    private String authPassword;
}
