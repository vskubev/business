package com.vskubev.business.client;

import com.vskubev.business.client.configuration.AuthConfig;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author skubev
 */
@Component
public class Authorization {

    private final AuthConfig authConfig;

    public Authorization(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public void auth() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("1.sign up");
        System.out.println("2.sign in");

        String answer = reader.readLine();

        if ("1".equals(answer)) {
            signUp();
        } else if ("2".equals(answer)) {
            signIn();
        }
    }

    public void signUp() {

    }

    public void signIn() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Enter login:");
        String login = reader.readLine();
        System.out.println("Enter password:");
        String password = reader.readLine();

        ResourceOwnerPasswordResourceDetails clientDetails = new ResourceOwnerPasswordResourceDetails();
        clientDetails.setAccessTokenUri(authConfig.getAuthServiceUrl());
        clientDetails.setClientId(authConfig.getClientId());
        clientDetails.setClientSecret(authConfig.getClientSecret());
        clientDetails.setGrantType(authConfig.getGrantType());
        clientDetails.setUsername(login);
        clientDetails.setPassword(password);

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientDetails);
        OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();

        int k = 10;
    }
}
