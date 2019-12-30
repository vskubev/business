package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.map.CategoryDTO;
import com.vskubev.business.businessservice.map.CategoryMapper;
import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
public class CategoryServiceImpl implements CrudService<CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        checkInput(categoryDTO);
        checkCategoryUniqueness(categoryDTO);

        Category category = categoryMapper.toEntity(categoryDTO);

        LocalDateTime localDateTime = LocalDateTime.now();
        category.setCreatedAt(localDateTime);
        category.setUpdatedAt(localDateTime);

        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO update(long id, CategoryDTO categoryDTO) {
        checkInputWithoutNPE(categoryDTO);
        checkCategoryUniqueness(categoryDTO);

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            if (new Long(categoryDTO.getOwnerId()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner cannot be changed");
            }
            if (categoryDTO.getName() != null) {
                category.get().setName(categoryDTO.getName());
            }

            category.get().setUpdatedAt(LocalDateTime.now());
            return categoryMapper.toDTO(categoryRepository.save(category.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not found"));
        return categoryMapper.toDTO(category);
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void checkInput(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (categoryDTO.getName() == null
                || categoryDTO.getName().isEmpty()
                || !categoryDTO.getName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The category name is incorrect. At least one upper case English letter.");
        }
    }

    private void checkInputWithoutNPE(CategoryDTO categoryDTO) {
        if (!(categoryDTO.getName() == null)
                && !categoryDTO.getName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The category name is incorrect. At least one upper case English letter.");
        }
    }

    private void checkCategoryUniqueness(@NotNull CategoryDTO categoryDTO) {
        if (isCategoryExist(categoryDTO.getName())) {
            String error = String.format("The category named %s already exists", categoryDTO.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private boolean isCategoryExist(@NotNull String name) {
        return categoryRepository.findByName(name).isPresent();
    }

}
