package com.vskubev.business.authservice.service;

import com.vskubev.business.authservice.model.User;

/**
 * @author skubev
 */
public interface SecurityService {
    /**
     * return current user or throw error
     * @return user entity
     * @throws org.springframework.web.server.ResponseStatusException 403 if not authorized
     */
    public User getCurrentUser();
}
