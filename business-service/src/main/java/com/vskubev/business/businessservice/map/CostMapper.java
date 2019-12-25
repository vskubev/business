package com.vskubev.business.businessservice.map;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

/**
 * @author skubev
 */
@Component
public class CostMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CostMapper(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public CostDTO toDto(@NotNull Cost entity) {
        CostDTO costDTO = new CostDTO(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getPrice(),
                entity.getCategory().getId(),
                entity.getOwner().getId()
        );
        return costDTO;
    }

    public Cost toEntity(@NotNull CostDTO costDTO) {
        User owner = userRepository.findById(costDTO.getOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        Category category = categoryRepository.findById(costDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        Cost cost = new Cost(
                costDTO.getCreatedAt(),
                costDTO.getUpdatedAt(),
                costDTO.getPrice(),
                category,
                owner
        );
        return cost;
    }
}
