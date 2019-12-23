package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author skubev
 */
@Component
public class UserDTO {

    private String login;
    private String hashPassword;
    private String name;
    private String email;

    /**
     * хз непонятно, нужны ли здесь эти переменные, если я их объявляю после создания ДТО объекта
     */
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDTO(String login, String hashPassword, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
