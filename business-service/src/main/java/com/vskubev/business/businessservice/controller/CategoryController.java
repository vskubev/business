package com.vskubev.business.businessservice.controller;

import com.google.gson.Gson;
import com.vskubev.business.businessservice.map.CategoryDTO;
import com.vskubev.business.businessservice.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author skubev
 */
@RestController
@Slf4j
public class CategoryController {
    private final Gson gson;
    private final CategoryServiceImpl categoryService;

    public CategoryController(Gson gson, CategoryServiceImpl categoryService) {
        this.gson = gson;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(categoryDTO));
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> update(@PathVariable("categoryId") long categoryId,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("Request: Update category: {}", gson.toJson(categoryDTO));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.update(categoryId, categoryDTO));
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("categoryId") long categoryId) {

        categoryService.deleteById(categoryId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<CategoryDTO> getById(@PathVariable("categoryId") long categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getById(categoryId));
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getCategories());
    }

}
