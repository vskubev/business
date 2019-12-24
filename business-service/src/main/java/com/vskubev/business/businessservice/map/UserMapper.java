package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class UserMapper {

    public UserDTO toDto(@NotNull User entity) {
        UserDTO userDTO = new UserDTO(
                entity.getId(),
                entity.getLogin(),
                "",
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User(
                userDTO.getLogin(),
                userDTO.getPassword(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getCreatedAt(),
                userDTO.getUpdatedAt()
        );
        return user;
    }

}
