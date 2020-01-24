package com.vskubev.business.client;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author skubev
 */
@Component
public class MainLogic {

    private final Authorization authorization;

    public MainLogic(Authorization authorization) {
        this.authorization = authorization;
    }

    @PostConstruct
    public void main() throws IOException {
        boolean isAuth = false;
        while (true) {
            if (!isAuth) {
                authorization.auth();
            }
        }
    }
}
