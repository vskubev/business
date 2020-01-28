package com.vskubev.business.client.logic.user;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpdateUserActStrategyImpl implements UserActStrategy {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public UpdateUserActStrategyImpl(UserServiceClient userServiceClient,
                                     Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {

    }
}
