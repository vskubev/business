package com.vskubev.business.client.logic.category;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public interface CategoryActStrategy {

    void act(OAuth2AccessToken token) throws IOException;
}
