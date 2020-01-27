package com.vskubev.business.client.logic;

import com.google.gson.Gson;
import com.vskubev.business.client.client.UserServiceClient;
import com.vskubev.business.client.enums.CategoryMethods;
import com.vskubev.business.client.enums.CostMethods;
import com.vskubev.business.client.enums.UserMethods;
import com.vskubev.business.client.map.UserDTO;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MethodService {

    private final UserServiceClient userServiceClient;
    private final Gson gson;

    public MethodService(UserServiceClient userServiceClient,
                         Gson gson) {
        this.userServiceClient = userServiceClient;
        this.gson = gson;
    }

    public void selectMethod(OAuth2AccessToken token) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Arrays.asList(UserMethods.values()).forEach(System.out::println);
        Arrays.asList(CategoryMethods.values()).forEach(System.out::println);
        Arrays.asList(CostMethods.values()).forEach(System.out::println);

        String selectMethod = reader.readLine();

        if (UserMethods.GET_USER_BY_ID.name().equals(selectMethod)) {
            System.out.println("Enter user number");
            long userId = Long.parseLong(reader.readLine());
            Optional<UserDTO> userDTO = userServiceClient.getUserById(userId, token);
            if (userDTO.isPresent()) {
                System.out.println(gson.toJson(userDTO.get()));
            } else {
                System.out.println("User is not found");
            }
        } else if (UserMethods.GET_CURRENT_USER.name().equals(selectMethod)) {
            Optional<UserDTO> userDTO = userServiceClient.getCurrentUser(token);
            if (userDTO.isPresent()) {
                System.out.println(gson.toJson(userDTO.get()));
            } else {
                System.out.println("User is not found");
            }
        } else if (UserMethods.GET_ALL_USERS.name().equals(selectMethod)) {
            Optional<List<UserDTO>> userDTOList = userServiceClient.getAll(token);
            if (userDTOList.isPresent()) {
                System.out.println(gson.toJson(userDTOList.get()));
            } else {
                System.out.println("User is not found");
            }
        }


    }
}
