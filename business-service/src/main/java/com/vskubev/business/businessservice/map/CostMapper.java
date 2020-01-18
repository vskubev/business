package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class CostMapper {
    private final CategoryRepository categoryRepository;

    public CostMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CostDTO toDTO(@NotNull Cost entity) {
        return new CostDTO(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getPrice(),
                entity.getCategory().getId(),
                entity.getOwnerId()
        );
    }

    public Cost toEntity(@NotNull CostDTO costDTO) {
        Category category = categoryRepository.findById(costDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        return new Cost(
                costDTO.getCreatedAt(),
                costDTO.getUpdatedAt(),
                costDTO.getPrice(),
                category,
                costDTO.getOwnerId()
        );
    }
}
