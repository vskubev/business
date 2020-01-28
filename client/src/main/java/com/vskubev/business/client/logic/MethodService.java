package com.vskubev.business.client.logic;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.enums.CategoryMethods;
import com.vskubev.business.client.enums.CostMethods;
import com.vskubev.business.client.enums.UserMethods;
import com.vskubev.business.client.logic.user.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author skubev
 */
@Component
public class MethodService {

    private final UserServiceClient userServiceClient;
    private final Gson gson;
    private final UserActStrategy createUserActStrategy;
    private final UserActStrategy updateUserActStrategy;
    private final UserActStrategy deleteUserActStrategy;
    private final UserActStrategy getUserActStrategy;
    private final UserActStrategy getCurrentUserActStrategy;
    private final UserActStrategy getAllUserActStrategy;

    public MethodService(UserServiceClient userServiceClient,
                         Gson gson,
                         CreateUserActStrategyImpl createUserActStrategy,
                         UpdateUserActStrategyImpl updateUserActStrategy,
                         DeleteUserActStrategyImpl deleteUserActStrategy,
                         GetUserActStrategyImpl getUserActStrategy,
                         GetCurrentUserActStrategyImpl getCurrentUserActStrategy,
                         GetAllUserActStrategyImpl getAllUserActStrategy) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
        this.createUserActStrategy = createUserActStrategy;
        this.updateUserActStrategy = updateUserActStrategy;
        this.deleteUserActStrategy = deleteUserActStrategy;
        this.getUserActStrategy = getUserActStrategy;
        this.getCurrentUserActStrategy = getCurrentUserActStrategy;
        this.getAllUserActStrategy = getAllUserActStrategy;
    }

    public void selectMethod(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Arrays.asList(UserMethods.values()).forEach(System.out::println);
        Arrays.asList(CategoryMethods.values()).forEach(System.out::println);
        Arrays.asList(CostMethods.values()).forEach(System.out::println);

        System.out.println();

        String selectMethod = reader.readLine().toUpperCase();

        if (UserMethods.CREATE_USER.name().equals(selectMethod)) {
            createUserActStrategy.act(token);
        } else if (UserMethods.UPDATE_USER.name().equals(selectMethod)) {
            updateUserActStrategy.act(token);
        } else if (UserMethods.DELETE_USER.name().equals(selectMethod)) {
            deleteUserActStrategy.act(token);
        } else if (UserMethods.GET_USER_BY_ID.name().equals(selectMethod)) {
            getUserActStrategy.act(token);
        } else if (UserMethods.GET_CURRENT_USER.name().equals(selectMethod)) {
            getCurrentUserActStrategy.act(token);
        } else if (UserMethods.GET_ALL_USERS.name().equals(selectMethod)) {
            getAllUserActStrategy.act(token);
        }
    }
}
