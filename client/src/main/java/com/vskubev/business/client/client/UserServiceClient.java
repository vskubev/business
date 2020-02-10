package com.vskubev.business.client.client;

import com.google.gson.Gson;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

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

    public HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
        String serialized = gson.toJson(data);
        return HttpRequest.BodyPublishers.ofString(serialized);
    }

    public Optional<UserDTO> create(final String login,
                                    final String password,
                                    final String name,
                                    final String email) {
        Map<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("name", name);
        data.put("email", email);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create("http://localhost:9090/users"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
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

    public Optional<UserDTO> update(final String login,
                                    final String password,
                                    final String name,
                                    final String email,
                                    final long userId,
                                    final OAuth2AccessToken token) {
        if (login != null && !login.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            if (!login.equals("")) {
                data.put("login", login);
            }
            if (!password.equals("")) {
                data.put("password", password);
            }
            if (!name.equals("")) {
                data.put("name", name);
            }
            if (!email.equals("")) {
                data.put("email", email);
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(ofFormData(data))
                    .uri(URI.create("http://localhost:9090/users/" + userId))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .build();

            HttpResponse<String> response;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return Optional.empty();
            }

            if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                UserDTO userDTO = gson.fromJson(response.body(), UserDTO.class);
                return Optional.of(userDTO);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void delete(final long userId,
                       final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("http://localhost:9090/users/" + userId))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Optional<UserDTO> getUser(final long userId,
                                     final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token)
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

    public Optional<UserDTO> getCurrentUser(final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token)
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

    public List<UserDTO> getAll(final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("http://localhost:9090/users"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return Arrays.asList(gson.fromJson(response.body(), UserDTO[].class));
        } else {
            return Collections.emptyList();
        }
    }
}
