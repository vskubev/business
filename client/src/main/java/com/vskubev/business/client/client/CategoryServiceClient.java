package com.vskubev.business.client.client;

import com.google.gson.Gson;
import com.vskubev.business.client.map.CategoryDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
public class CategoryServiceClient {

    private final HttpClient httpClient;
    private final Gson gson;

    public CategoryServiceClient(HttpClient httpClient,
                                 Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
        String serialized = gson.toJson(data);
        return HttpRequest.BodyPublishers.ofString(serialized);
    }

    public Optional<CategoryDTO> create(final String name,
                                        final String ownerId,
                                        final OAuth2AccessToken token) {
        if (name != null && !name.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("name", name);
            data.put("ownerId", ownerId);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(ofFormData(data))
                    .uri(URI.create("http://localhost:9091/categories"))
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
                CategoryDTO categoryDTO = gson.fromJson(response.body(), CategoryDTO.class);
                return Optional.of(categoryDTO);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public Optional<CategoryDTO> update(final String name,
                                        final long categoryId,
                                        final OAuth2AccessToken token) {
        Map<String, String> data = new HashMap<>();
        if (!name.equals("")) {
            data.put("name", name);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(ofFormData(data))
                .uri(URI.create("http://localhost:9091/categories/" + categoryId))
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
            CategoryDTO categoryDTO = gson.fromJson(response.body(), CategoryDTO.class);
            return Optional.of(categoryDTO);
        } else {
            return Optional.empty();
        }
    }

    public void delete(final long categoryId,
                       final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("http://localhost:9091/categories/" + categoryId))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Optional<CategoryDTO> getCategory(final long categoryId,
                                             final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9091/categories/" + categoryId))
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
            CategoryDTO categoryDTO = gson.fromJson(response.body(), CategoryDTO.class);
            return Optional.of(categoryDTO);
        } else {
            return Optional.empty();
        }
    }

    public List<CategoryDTO> getAll(final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9091/categories"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return Arrays.asList(gson.fromJson(response.body(), CategoryDTO[].class));
        } else {
            return Collections.emptyList();
        }
    }
}
