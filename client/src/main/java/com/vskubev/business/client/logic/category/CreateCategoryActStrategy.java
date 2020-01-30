package com.vskubev.business.client.logic.category;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CategoryServiceClient;
import com.vskubev.business.client.logic.user.UserActStrategy;
import com.vskubev.business.client.map.CategoryDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class CreateCategoryActStrategy implements UserActStrategy {

    private final CategoryServiceClient categoryServiceClient;
    private final Gson gson;

    public CreateCategoryActStrategy(CategoryServiceClient categoryServiceClient,
                                     Gson gson) {
        this.categoryServiceClient = categoryServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter name");
        final String name = reader.readLine();

        System.out.println("Enter ownerId");
        final String ownerId = reader.readLine();

        Optional<CategoryDTO> categoryDTO = categoryServiceClient.create(name, ownerId, token);
        if (categoryDTO.isPresent()) {
            System.out.println(gson.toJson(categoryDTO.get()));
        } else {
            System.out.println("Category is not found");
        }
    }
}
