package com.vskubev.business.businessservice.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author skubev
 */
@Component
@ConfigurationProperties("api")
@Data
@NoArgsConstructor
public class ApiConfig {
    private String authServiceUrl;
}
