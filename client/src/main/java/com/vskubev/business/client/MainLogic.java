package com.vskubev.business.client;

import com.vskubev.business.client.configuration.AuthConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.http.HttpClient;

/**
 * @author skubev
 */
@Component
public class MainLogic {

    private final HttpClient httpClient;
    private final AuthConfig authConfig;
    private final Authorization authorization;

    public MainLogic(HttpClient httpClient, AuthConfig authConfig, Authorization authorization) {
        this.httpClient = httpClient;
        this.authConfig = authConfig;
        this.authorization = authorization;
    }

    @PostConstruct
    public void main() throws IOException {
        boolean isAuth = false;
        while (true) {
            if (!isAuth) {
                authorization.auth();
            }
        }
    }
}
