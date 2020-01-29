package com.vskubev.business.client.logic.user;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public interface UserActStrategy {

    void act(final OAuth2AccessToken token) throws IOException;
}
