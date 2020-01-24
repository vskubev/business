package com.vskubev.business.client.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author skubev
 */
@Component
@ConfigurationProperties("auth")
@Data
@NoArgsConstructor
public class AuthConfig {
    private String basic;
    private String authServiceUrl;
    private String clientId;
    private String clientSecret;
    private String grantType;
}
