package com.vskubev.business.authservice.map;

import com.vskubev.business.authservice.model.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class UserMapper {

    /**
     * map to DTO object
     *
     * @param entity
     * @return
     */
    public UserDTO toDTO(@NotNull User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getLogin(),
                "",
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * map to Entity
     *
     * @param userDTO
     * @return
     */
    public User toEntity(@NotNull UserDTO userDTO) {
        return new User(
                userDTO.getLogin(),
                userDTO.getPassword(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getCreatedAt(),
                userDTO.getUpdatedAt()
        );
    }

}
