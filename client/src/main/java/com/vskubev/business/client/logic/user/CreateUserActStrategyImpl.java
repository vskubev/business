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

@Component
public class CreateUserActStrategyImpl implements UserActStrategy {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public CreateUserActStrategyImpl(UserServiceClient userServiceClient,
                                     Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    @Override
    public void act(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter login");
        String login = reader.readLine();

        System.out.println("Enter password");
        String password = reader.readLine();

        System.out.println("Enter name");
        String name = reader.readLine();

        System.out.println("Enter email");
        String email = reader.readLine();

        Optional<UserDTO> userDTO = userServiceClient.create(login, password, name, email);
        if (userDTO.isPresent()) {
            System.out.println(gson.toJson(userDTO.get()));
        } else {
            System.out.println("User is not found");
        }
    }
}
