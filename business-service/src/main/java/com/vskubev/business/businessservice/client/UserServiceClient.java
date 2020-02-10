package com.vskubev.business.businessservice.client;


import com.google.gson.Gson;
import com.vskubev.business.businessservice.configuration.ApiConfig;
import com.vskubev.business.businessservice.map.UserDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class UserServiceClient {
    private final HttpClient httpClient;
    private final Gson gson;
    private final ApiConfig apiConfig;

    public UserServiceClient(HttpClient httpClient,
                             Gson gson,
                             ApiConfig apiConfig) {
        this.httpClient = httpClient;
        this.gson = gson;
        this.apiConfig = apiConfig;
    }

    public Optional<UserDTO> getUserById(final long userId, final String token) {
        StringBuilder requestUri = new StringBuilder();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", token)
                .uri(URI.create(requestUri
                        .append(apiConfig.getAuthServiceUrl())
                        .append("/users/")
                        .append(userId)
                        .toString()))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        /**
         * Пулл коннектов переполнен или сервис недоступен
         */
            catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            UserDTO userDTO = gson.fromJson(response.body(), UserDTO.class);
            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
    }

    public Optional<UserDTO> getCurrentUser(final String token) {
        StringBuilder requestUri = new StringBuilder();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", token)
                .uri(URI.create(requestUri
                        .append(apiConfig.getAuthServiceUrl())
                        .append("/users/current")
                        .toString()))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        /**
         * Пулл коннектов переполнен или сервис недоступен
         */
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            UserDTO userDTO = gson.fromJson(response.body(), UserDTO.class);
            return Optional.of(userDTO);
        } else {
            return Optional.empty();
        }
    }
}
