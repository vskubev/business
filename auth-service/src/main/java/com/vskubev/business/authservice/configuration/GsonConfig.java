package com.vskubev.business.authservice.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author skubev
 */
@Configuration
public class GsonConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }

}
