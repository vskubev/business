package com.vskubev.business.client.logic;

import com.vskubev.business.client.enums.CategoryMethods;
import com.vskubev.business.client.enums.CostMethods;
import com.vskubev.business.client.enums.UserMethods;
import com.vskubev.business.client.logic.category.*;
import com.vskubev.business.client.logic.cost.*;
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
public class UserActivityFacade {

    private final UserActStrategy createUserActStrategy;
    private final UserActStrategy updateUserActStrategy;
    private final UserActStrategy deleteUserActStrategy;
    private final UserActStrategy getUserActStrategy;
    private final UserActStrategy getCurrentUserActStrategy;
    private final UserActStrategy getAllUserActStrategy;
    private final UserActStrategy getCategoryActStrategy;
    private final UserActStrategy getAllCategoryActStrategy;
    private final UserActStrategy deleteCategoryActStrategy;
    private final UserActStrategy createCategoryActStrategy;
    private final UserActStrategy updateCategoryActStrategy;
    private final UserActStrategy getAllCostActStrategy;
    private final UserActStrategy getAllCostUserActStrategy;
    private final UserActStrategy getCostActStrategy;
    private final UserActStrategy createCostActStrategy;
    private final UserActStrategy updateCostActStrategy;
    private final UserActStrategy deleteCostActStrategy;

    public UserActivityFacade(CreateUserActStrategy createUserActStrategy,
                              UpdateUserActStrategy updateUserActStrategy,
                              DeleteUserActStrategy deleteUserActStrategy,
                              GetUserActStrategy getUserActStrategy,
                              GetCurrentUserActStrategy getCurrentUserActStrategy,
                              GetAllUserActStrategy getAllUserActStrategy,
                              GetCategoryActStrategy getCategoryActStrategy,
                              GetAllCategoryActStrategy getAllCategoryActStrategy,
                              DeleteCategoryActStrategy deleteCategoryActStrategy,
                              CreateCategoryActStrategy createCategoryActStrategy,
                              UpdateCategoryActStrategy updateCategoryActStrategy,
                              GetAllCostActStrategy getAllCostActStrategy,
                              GetAllCostUserActStrategy getAllCostUserActStrategy,
                              GetCostActStrategy getCostActStrategy,
                              CreateCostActStrategy createCostActStrategy,
                              UpdateCostActStrategy updateCostActStrategy,
                              DeleteCostActStrategy deleteCostActStrategy) {
        this.createUserActStrategy = createUserActStrategy;
        this.updateUserActStrategy = updateUserActStrategy;
        this.deleteUserActStrategy = deleteUserActStrategy;
        this.getUserActStrategy = getUserActStrategy;
        this.getCurrentUserActStrategy = getCurrentUserActStrategy;
        this.getAllUserActStrategy = getAllUserActStrategy;
        this.getCategoryActStrategy = getCategoryActStrategy;
        this.getAllCategoryActStrategy = getAllCategoryActStrategy;
        this.deleteCategoryActStrategy = deleteCategoryActStrategy;
        this.createCategoryActStrategy = createCategoryActStrategy;
        this.updateCategoryActStrategy = updateCategoryActStrategy;
        this.getAllCostActStrategy = getAllCostActStrategy;
        this.getAllCostUserActStrategy = getAllCostUserActStrategy;
        this.getCostActStrategy = getCostActStrategy;
        this.createCostActStrategy = createCostActStrategy;
        this.updateCostActStrategy = updateCostActStrategy;
        this.deleteCostActStrategy = deleteCostActStrategy;
    }

    public void selectMethod(final OAuth2AccessToken token) throws IOException {
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
        } else if (CategoryMethods.CREATE_CATEGORY.name().equals(selectMethod)) {
            createCategoryActStrategy.act(token);
        } else if (CategoryMethods.UPDATE_CATEGORY.name().equals(selectMethod)) {
            updateCategoryActStrategy.act(token);
        } else if (CategoryMethods.DELETE_CATEGORY.name().equals(selectMethod)) {
            deleteCategoryActStrategy.act(token);
        } else if (CategoryMethods.GET_CATEGORY_BY_ID.name().equals(selectMethod)) {
            getCategoryActStrategy.act(token);
        } else if (CategoryMethods.GET_ALL_CATEGORIES.name().equals(selectMethod)) {
            getAllCategoryActStrategy.act(token);
        } else if (CostMethods.CREATE_COST.equals(selectMethod)) {
            createCostActStrategy.act(token);
        } else if (CostMethods.UPDATE_COST.equals(selectMethod)) {
            updateCostActStrategy.act(token);
        } else if (CostMethods.DELETE_COST.equals(selectMethod)) {
            deleteCostActStrategy.act(token);
        } else if (CostMethods.GET_COST_BY_ID.equals(selectMethod)) {
            getCostActStrategy.act(token);
        } else if (CostMethods.GET_ALL_COSTS_USER.equals(selectMethod)) {
            getAllCostUserActStrategy.act(token);
        } else if (CostMethods.GET_ALL_COSTS.equals(selectMethod)) {
            getAllCostActStrategy.act(token);
        }
    }
}
