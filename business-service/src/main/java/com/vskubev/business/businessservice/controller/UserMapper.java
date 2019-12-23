package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class UserMapper {

    public UserDTO toDto(@NotNull User entity) {
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getLogin(), entity.getHashPassword(), entity.getName(), entity.getEmail(), entity.getCreatedAt(), entity.getUpdatedAt());
        return userDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        return null;
    }

}
