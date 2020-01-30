package com.vskubev.business.client.logic.user;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class UpdateUserActStrategy implements UserActStrategy {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public UpdateUserActStrategy(UserServiceClient userServiceClient,
                                 Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Optional<UserDTO> userDTO = userServiceClient.getCurrentUser(token);
        final long userId = userDTO.get().getId();

        System.out.println("Enter login");
        final String login = reader.readLine();

        System.out.println("Enter password");
        final String password = reader.readLine();

        System.out.println("Enter name");
        final String name = reader.readLine();

        System.out.println("Enter email");
        final String email = reader.readLine();

        Optional<UserDTO> userDTOUpdate = userServiceClient.update(login, password, name, email, userId, token);

        System.out.println(gson.toJson(userDTOUpdate.get()));
    }
}
