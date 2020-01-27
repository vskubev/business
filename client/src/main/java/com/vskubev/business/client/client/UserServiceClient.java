package com.vskubev.business.client.client;

import com.google.gson.Gson;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class UserServiceClient {
    private final HttpClient httpClient;
    private final Gson gson;

    public UserServiceClient(HttpClient httpClient,
                             Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public Optional<UserDTO> getUserById(final long userId, OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9090/users/" + userId))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        /**
         * Пулл коннектов переполнен или сервис недоступен
         */ catch (IOException | InterruptedException e) {
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

    public Optional<UserDTO> getCurrentUser(OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9090/users/current"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        /**
         * Пулл коннектов переполнен или сервис недоступен
         */ catch (IOException | InterruptedException e) {
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

    public Optional<List<UserDTO>> getAll(OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9090/users"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            List<UserDTO> userDTOList = gson.fromJson(response.body(), (Type) UserDTO.class);
            return Optional.of(userDTOList);
        } else {
            return Optional.empty();
        }
    }
}
