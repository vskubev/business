package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * @author skubev
 */
@Service
public class CategoryServiceImpl implements CrudService<Category> {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        LocalDateTime localDateTime = LocalDateTime.now();
        category.setCreatedAt(localDateTime);
        category.setUpdatedAt(localDateTime);

        return categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
