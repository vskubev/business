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

    public CategoryDTO toDto(@NotNull Category entity) {
        CategoryDTO categoryDTO = new CategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getOwner().getId()
        );
        return categoryDTO;
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        User owner = userRepository.findById(categoryDTO.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "..."));

        Category category = new Category(
                categoryDTO.getName(),
                categoryDTO.getCreatedAt(),
                categoryDTO.getUpdatedAt(),
                owner
        );
        return category;
    }

}
