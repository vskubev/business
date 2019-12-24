package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.map.CategoryDTO;
import com.vskubev.business.businessservice.map.CategoryMapper;
import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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

        Category category = categoryMapper.toEntity(categoryDTO);

        LocalDateTime localDateTime = LocalDateTime.now();
        category.setCreatedAt(localDateTime);
        category.setUpdatedAt(localDateTime);

        return categoryMapper.toDto(categoryRepository.saveAndFlush(category));
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));
        return categoryMapper.toDto(category);
    }

    private void checkInput(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (categoryDTO.getName() == null
                || categoryDTO.getName().isEmpty()
                || !categoryDTO.getName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Name is incorrect. At least one upper case English letter");
        }
    }
}
