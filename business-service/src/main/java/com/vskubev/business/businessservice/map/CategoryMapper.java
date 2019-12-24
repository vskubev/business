package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class CategoryMapper {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    public CategoryMapper(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
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
        User owner = obtainCategory(categoryDTO);

        Category category = new Category(
                categoryDTO.getName(),
                categoryDTO.getCreatedAt(),
                categoryDTO.getUpdatedAt(),
                owner
        );
        return category;
    }

    private User obtainCategory(CategoryDTO categoryDTO) {
        return userMapper.toEntity(userService.getById(categoryDTO.getOwnerId()));
    }

}
