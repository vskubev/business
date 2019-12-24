package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.map.CategoryDTO;
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
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("categoryId") long categoryId) {
        categoryService.deleteById(categoryId);
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public CategoryDTO getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getById(categoryId);
    }

}
