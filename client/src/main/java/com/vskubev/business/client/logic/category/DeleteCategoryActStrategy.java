package com.vskubev.business.client.logic.category;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CategoryServiceClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class DeleteCategoryActStrategy implements CategoryActStrategy {

    private final CategoryServiceClient categoryServiceClient;
    private final Gson gson;

    public DeleteCategoryActStrategy(CategoryServiceClient categoryServiceClient,
                                     Gson gson) {
        this.categoryServiceClient = categoryServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter category id");
        final long categoryId = Long.parseLong(reader.readLine());
        categoryServiceClient.delete(categoryId, token);
    }
}
