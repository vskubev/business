package com.vskubev.business.client.logic.cost;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CostServiceClient;
import com.vskubev.business.client.map.CostDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class CreateCostActStrategy implements CostActStrategy {

    private final CostServiceClient costServiceClient;
    private final Gson gson;

    public CreateCostActStrategy(CostServiceClient costServiceClient,
                                 Gson gson) {
        this.costServiceClient = costServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter price");
        final String price = reader.readLine();

        System.out.println("Enter ownerId");
        final String ownerId = reader.readLine();

        System.out.println("Enter categoryId");
        final String categoryId = reader.readLine();

        Optional<CostDTO> costDTO = costServiceClient.create(price, ownerId, categoryId, token);
        if (costDTO.isPresent()) {
            System.out.println(gson.toJson(costDTO.get()));
        } else {
            System.out.println("Cost is not found");
        }
    }
}
