package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class CategoryMapper {
    private final UserRepository userRepository;

    public CategoryMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * map to DTO object
     *
     * @param entity
     * @return
     */
    public CategoryDTO toDTO(@NotNull Category entity) {
        return new CategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getOwner().getId()
        );
    }

    /**
     * map to Entity
     *
     * @param categoryDTO
     * @return
     */
    public Category toEntity(@NotNull CategoryDTO categoryDTO) {
        User owner = userRepository.findById(categoryDTO.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found"));

        return new Category(
                categoryDTO.getName(),
                categoryDTO.getCreatedAt(),
                categoryDTO.getUpdatedAt(),
                owner
        );
    }

}
