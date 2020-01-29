package com.vskubev.business.client.client;

import com.google.gson.Gson;
import com.vskubev.business.client.map.CostDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
public class CostServiceClient {

    private final HttpClient httpClient;
    private final Gson gson;

    public CostServiceClient(HttpClient httpClient,
                             Gson gson) {
        this.httpClient = httpClient;
        this.gson = gson;
    }

    public HttpRequest.BodyPublisher ofFormData(Map<String, String> data) {
        String serialized = gson.toJson(data);
        return HttpRequest.BodyPublishers.ofString(serialized);
    }

    public Optional<CostDTO> create(final String price,
                                    final String ownerId,
                                    final String categoryId,
                                    final OAuth2AccessToken token) {
        Map<String, String> data = new HashMap<>();
        data.put("price", price);
        data.put("ownerId", ownerId);
        data.put("categoryId", categoryId);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create("http://localhost:9091/costs"))
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
            CostDTO costDTO = gson.fromJson(response.body(), CostDTO.class);
            return Optional.of(costDTO);
        } else {
            return Optional.empty();
        }
    }

    public Optional<CostDTO> update(final String price,
                                    final String categoryId,
                                    final long costId,
                                    final OAuth2AccessToken token) {
        Map<String, String> data = new HashMap<>();
        if (!price.equals("")) {
            data.put("price", price);
        }
        if (!categoryId.equals("")) {
            data.put("categoryId", categoryId);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(ofFormData(data))
                .uri(URI.create("http://localhost:9091/costs/" + costId))
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
            CostDTO costDTO = gson.fromJson(response.body(), CostDTO.class);
            return Optional.of(costDTO);
        } else {
            return Optional.empty();
        }
    }

    public void delete(final long costId,
                       final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create("http://localhost:9091/costs/" + costId))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Optional<CostDTO> getCost(final long costId,
                                     final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9091/costs/" + costId))
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
            CostDTO costDTO = gson.fromJson(response.body(), CostDTO.class);
            return Optional.of(costDTO);
        } else {
            return Optional.empty();
        }
    }

    public List<CostDTO> getAllCostsUser(final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9091/costs"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return Arrays.asList(gson.fromJson(response.body(), CostDTO[].class));
        } else {
            return Collections.emptyList();
        }
    }

    public List<CostDTO> getAll(final OAuth2AccessToken token) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Authorization", "Bearer " + token.toString())
                .uri(URI.create("http://localhost:9091/costs/all"))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            return Arrays.asList(gson.fromJson(response.body(), CostDTO[].class));
        } else {
            return Collections.emptyList();
        }
    }
}
