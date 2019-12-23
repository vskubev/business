package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.service.impl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author skubev
 */
@RestController
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryService.create(category);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("categoryId") long categoryId) {
        categoryService.deleteById(categoryId);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getById(categoryId);
    }

}
