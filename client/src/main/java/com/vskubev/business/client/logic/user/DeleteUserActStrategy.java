package com.vskubev.business.client.logic.user;

import com.vskubev.business.client.client.UserServiceClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author skubev
 */
@Component
public class DeleteUserActStrategy implements UserActStrategy {

    private final UserServiceClient userServiceClient;

    public DeleteUserActStrategy(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public void act(final OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter user number");
        final long userId = Long.parseLong(reader.readLine());
        userServiceClient.delete(userId, token);
    }
}
