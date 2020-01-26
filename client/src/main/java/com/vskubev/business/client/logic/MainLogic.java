package com.vskubev.business.client.logic;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.enums.CategoryMethodsEnum;
import com.vskubev.business.client.enums.CostMethodsEnum;
import com.vskubev.business.client.enums.UserMethodsEnum;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author skubev
 */
@Component
public class MainLogic {

    private final Authorization authorization;
    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public MainLogic(Authorization authorization,
                     UserServiceClient userServiceClient, Gson gson) {
        this.authorization = authorization;
        this.userServiceClient = userServiceClient;
        this.gson = gson;
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

            Arrays.asList(UserMethodsEnum.values()).forEach(System.out::println);
            Arrays.asList(CategoryMethodsEnum.values()).forEach(System.out::println);
            Arrays.asList(CostMethodsEnum.values()).forEach(System.out::println);

            String result = reader.readLine();

            if ("GET_USER_BY_ID".equals(result)) {
                System.out.println("Enter user number");
                long userId = Long.parseLong(reader.readLine());
                Optional<UserDTO> userDTO = userServiceClient.getUserById(userId, token);
                if (userDTO.isPresent()) {
                    System.out.println(gson.toJson(userDTO.get()));
                }
            }

            System.out.println("One more operation? y/n");

            String moreOperation = reader.readLine();

            if ("n".equals(moreOperation)) {
                break;
            }

        }
    }
}
