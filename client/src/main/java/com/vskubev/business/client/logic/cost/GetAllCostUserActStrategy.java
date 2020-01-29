package com.vskubev.business.client.logic.cost;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CostServiceClient;
import com.vskubev.business.client.map.CostDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetAllCostUserActStrategy implements CostActStrategy{

    private final CostServiceClient costServiceClient;
    private final Gson gson;

    public GetAllCostUserActStrategy(CostServiceClient costServiceClient,
                                     Gson gson) {
        this.costServiceClient = costServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        List<CostDTO> costDTOList = costServiceClient.getAllCostsUser(token);
        System.out.println(gson.toJson(costDTOList));
    }
}
