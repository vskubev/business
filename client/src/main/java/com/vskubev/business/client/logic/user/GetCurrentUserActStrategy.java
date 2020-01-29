package com.vskubev.business.client.logic.user;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class GetCurrentUserActStrategy implements UserActStrategy {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public GetCurrentUserActStrategy(UserServiceClient userServiceClient,
                                     Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        Optional<UserDTO> userDTO = userServiceClient.getCurrentUser(token);

        if (userDTO.isPresent()) {
            System.out.println(gson.toJson(userDTO.get()));
        } else {
            System.out.println("User is not found");
        }
    }
}
