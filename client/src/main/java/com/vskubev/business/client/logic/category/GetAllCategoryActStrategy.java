package com.vskubev.business.client.logic.category;

import com.google.gson.Gson;
import com.vskubev.business.client.client.CategoryServiceClient;
import com.vskubev.business.client.map.CategoryDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetAllCategoryActStrategy implements CategoryActStrategy {

    private final CategoryServiceClient categoryServiceClient;
    private final Gson gson;

    public GetAllCategoryActStrategy(CategoryServiceClient categoryServiceClient,
                                     Gson gson) {
        this.categoryServiceClient = categoryServiceClient;
        this.gson = gson;
    }


    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        List<CategoryDTO> categoryDTOList = categoryServiceClient.getAll(token);
        System.out.println(gson.toJson(categoryDTOList));
    }
}
