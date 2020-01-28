package com.vskubev.business.client.logic;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.configuration.AuthConfig;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class Authorization {

    private final AuthConfig authConfig;
    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public Authorization(AuthConfig authConfig,
                         UserServiceClient userServiceClient,
                         Gson gson) {
        this.authConfig = authConfig;
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    public OAuth2AccessToken auth() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        OAuth2AccessToken token = null;

        System.out.println("1.sign up");
        System.out.println("2.sign in");

        String answer = reader.readLine();

        if ("1".equals(answer)) {
            signUp();
            token = null;
        } else if ("2".equals(answer)) {
            token = signIn();
        }
        return token;
    }

    //TODO избавиться от NPE при авторизации новым юзером
    public OAuth2AccessToken signUp() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Create new user:");

        System.out.println("Enter login");
        String login = reader.readLine();

        System.out.println("Enter password");
        String password = reader.readLine();

        System.out.println("Enter name");
        String name = reader.readLine();

        System.out.println("Enter email");
        String email = reader.readLine();

        Optional<UserDTO> userDTO = userServiceClient.create(login, password, name, email);
        if (userDTO.isPresent()) {
            System.out.println(gson.toJson(userDTO.get()));
        } else {
            System.out.println("User is not found");
        }
        userServiceClient.create(login, password, name, email);
        return signIn();
    }

    public OAuth2AccessToken signIn() throws IOException {
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

        OAuth2AccessToken token = null;
        try {
            OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientDetails);
            token = oAuth2RestTemplate.getAccessToken();
        } catch (Exception e) {
            System.out.println("Incorrect. Please, try again");
            System.out.println("1. Try one more time");
            System.out.println("2. Sign up");
            String result = reader.readLine();
            if ("1".equals(result)) {
                token = signIn();
            } else {
                token = signUp();
            }
        }
        return token;
    }
}
