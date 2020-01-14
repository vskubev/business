package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.Category;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class CategoryMapper {

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
                entity.getOwnerId()
        );
    }

    /**
     * map to Entity
     *
     * @param categoryDTO
     * @return
     */
    public Category toEntity(@NotNull CategoryDTO categoryDTO) {
        return new Category(
                categoryDTO.getName(),
                categoryDTO.getCreatedAt(),
                categoryDTO.getUpdatedAt(),
                categoryDTO.getOwnerId()
        );
    }

}
