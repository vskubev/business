package com.vskubev.business.client.logic.cost;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CostServiceClient;
import com.vskubev.business.client.logic.user.UserActStrategy;
import com.vskubev.business.client.map.CostDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class GetCostActStrategy implements UserActStrategy {

    private final CostServiceClient costServiceClient;
    private final Gson gson;

    public GetCostActStrategy(CostServiceClient costServiceClient,
                              Gson gson) {
        this.costServiceClient = costServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter cost id");
        final long costId = Long.parseLong(reader.readLine());

        Optional<CostDTO> costDTO = costServiceClient.getCost(costId, token);

        if (costDTO.isPresent()) {
            System.out.println(gson.toJson(costDTO.get()));
        } else {
            System.out.println("Cost is not found");
        }
    }
}
