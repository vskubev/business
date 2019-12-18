package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CrudService<Category> {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category entity) {
        return categoryRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
}
