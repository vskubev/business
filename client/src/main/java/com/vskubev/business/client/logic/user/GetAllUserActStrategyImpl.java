package com.vskubev.business.client.logic.user;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author skubev
 */
@Component
public class GetAllUserActStrategyImpl implements UserActStrategy {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public GetAllUserActStrategyImpl(UserServiceClient userServiceClient,
                                     Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        List<UserDTO> userDTOList = userServiceClient.getAll(token);
        if (userDTOList == null) {
            System.out.println("{}");
        } else {
            System.out.println(gson.toJson(userDTOList));
        }
    }
}
