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
public class UpdateCostActStrategy implements CostActStrategy {

    private final CostServiceClient costServiceClient;
    private final Gson gson;

    public UpdateCostActStrategy(CostServiceClient costServiceClient,
                                 Gson gson) {
        this.costServiceClient = costServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter cost id for update");
        final long costId = Long.parseLong(reader.readLine());

        System.out.println("Enter price");
        final String price = reader.readLine();

        System.out.println("Enter categoryId");
        final String categoryId = reader.readLine();

        Optional<CostDTO> costDTOUpdate = costServiceClient.update(price, categoryId, costId, token);

        System.out.println(gson.toJson(costDTOUpdate.get()));
    }
}
