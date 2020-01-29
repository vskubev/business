package com.vskubev.business.client.logic.cost;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public interface CostActStrategy {

    void act(OAuth2AccessToken token) throws IOException;
}
