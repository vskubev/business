package com.vskubev.business.client.logic.category;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CategoryServiceClient;
import com.vskubev.business.client.map.CategoryDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class GetCategoryActStrategy implements CategoryActStrategy {

    private final CategoryServiceClient categoryServiceClient;
    private final Gson gson;

    public GetCategoryActStrategy(CategoryServiceClient categoryServiceClient,
                                  Gson gson) {
        this.categoryServiceClient = categoryServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter category id");
        final long categoryId = Long.parseLong(reader.readLine());

        Optional<CategoryDTO> categoryDTO = categoryServiceClient.getCategory(categoryId, token);

        if (categoryDTO.isPresent()) {
            System.out.println(gson.toJson(categoryDTO.get()));
        } else {
            System.out.println("Category is not found");
        }
    }
}
