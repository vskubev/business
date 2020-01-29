package com.vskubev.business.client.logic;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author skubev
 */
@Component
public class MainLogic {

    private final Authorization authorization;
    private final UserServiceClient userServiceClient;
    private final Gson gson;
    private final UserActivityFacade userActivityFacade;

    public MainLogic(Authorization authorization,
                     UserServiceClient userServiceClient,
                     Gson gson,
                     UserActivityFacade userActivityFacade) {
        this.authorization = authorization;
        this.userServiceClient = userServiceClient;
        this.gson = gson;
        this.userActivityFacade = userActivityFacade;
    }

    @PostConstruct
    public void main() throws IOException {
        boolean isAuth = false;
        OAuth2AccessToken token = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if (!isAuth) {
                token = authorization.auth();
                isAuth = true;
            }

            userActivityFacade.selectMethod(token);

            System.out.println("One more operation? Y/N");
            if ("N".equals(reader.readLine().toUpperCase())) {

            }
        }
    }
}
