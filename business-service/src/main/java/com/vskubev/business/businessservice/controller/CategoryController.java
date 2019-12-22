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


    /**
     *
     * request:
     * {
     * 	"name": "test",
     * 	"owner": 9
     * }
     *
     * response:
     *{
     *     "timestamp": "2019-12-22T18:26:03.244+0000",
     *     "status": 400,
     *     "error": "Bad Request",
     *     "message": "JSON parse error: Cannot construct instance of `com.vskubev.business.businessservice.model.User` (although at least one Creator exists): no int/Int-argument constructor/factory method to deserialize from Number value (9); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.vskubev.business.businessservice.model.User` (although at least one Creator exists): no int/Int-argument constructor/factory method to deserialize from Number value (9)\n at [Source: (PushbackInputStream); line: 6, column: 11] (through reference chain: com.vskubev.business.businessservice.model.Category[\"owner\"])",
     *     "path": "/categories"
     * }
     *
     */
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
