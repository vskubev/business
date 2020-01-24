package com.vskubev.business.client;

import com.vskubev.business.client.configuration.AuthConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.http.HttpClient;

/**
 * @author skubev
 */
@Component
public class MainLogic {

    private final HttpClient httpClient;
    private final AuthConfig authConfig;
    private final Authorization authorization;

    public MainLogic(HttpClient httpClient, AuthConfig authConfig, Authorization authorization) {
        this.httpClient = httpClient;
        this.authConfig = authConfig;
        this.authorization = authorization;
    }

    @PostConstruct
    public void main() throws IOException {
        boolean isAuth = false;
        while (true) {
            if (!isAuth) {
                authorization.auth();

//                System.out.println("1.sign up");
//                System.out.println("2.sign in");
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(System.in));
//                String answer = reader.readLine();
//
//                if ("1".equals(answer)) {
//
//                } else if ("2".equals(answer)) {
//                    System.out.println("Enter login:");
//                    String login = reader.readLine();
//                    System.out.println("Enter password:");
//                    String password = reader.readLine();
//
//                    ResourceOwnerPasswordResourceDetails clientDetails = new ResourceOwnerPasswordResourceDetails();
//                    clientDetails.setAccessTokenUri("http://localhost:9090/oauth/token");
//                    clientDetails.setClientId("example");
//                    clientDetails.setClientSecret("examplesecret");
//                    clientDetails.setGrantType("password");
//                    clientDetails.setUsername(login);
//                    clientDetails.setPassword(password);
//
//                    OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientDetails);
//                    OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();
//
//                    int k = 10;
                }
        }
    }
}
