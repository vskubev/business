package com.vskubev.business.client.logic;

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
    private final UserActivityFacade userActivityFacade;

    public MainLogic(Authorization authorization,
                     UserActivityFacade userActivityFacade) {
        this.authorization = authorization;
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
