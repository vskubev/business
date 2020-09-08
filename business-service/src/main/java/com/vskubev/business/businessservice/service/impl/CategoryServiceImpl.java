package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.client.UserServiceClient;
import com.vskubev.business.businessservice.configuration.RabbitConfiguration;
import com.vskubev.business.businessservice.map.CategoryDTO;
import com.vskubev.business.businessservice.map.CategoryMapper;
import com.vskubev.business.businessservice.message.CreateCategoryMessage;
import com.vskubev.business.businessservice.message.MessageSender;
import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.repository.CategoryRepository;
import com.vskubev.business.businessservice.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CrudService<CategoryDTO> {

    @Autowired
    private HttpServletRequest request;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserServiceClient userServiceClient;
    private final MessageSender messageSender;
    private final RabbitConfiguration rabbitConfiguration;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper,
                               UserServiceClient userServiceClient,
                               MessageSender messageSender,
                               RabbitConfiguration rabbitConfiguration) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userServiceClient = userServiceClient;
        this.messageSender = messageSender;
        this.rabbitConfiguration = rabbitConfiguration;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public CategoryDTO create(CategoryDTO categoryDTO) {
        String token = request.getHeader("Authorization");

        checkInput(categoryDTO);
        checkCategoryUniqueness(categoryDTO);

        if (!userServiceClient.getUserById(categoryDTO.getOwnerId(), token).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found");
        }

        Category category = categoryMapper.toEntity(categoryDTO);

        LocalDateTime localDateTime = LocalDateTime.now();
        category.setCreatedAt(localDateTime);
        category.setUpdatedAt(localDateTime);

        Category createdCategory = categoryRepository.save(category);

        messageSender.sendMessage(rabbitConfiguration.getBusinessExchangeName(),
                rabbitConfiguration.getBusinessRoutingKey(),
                new CreateCategoryMessage(createdCategory.getName(), createdCategory.getOwnerId()));

        return categoryMapper.toDTO(createdCategory);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public CategoryDTO update(long id, CategoryDTO categoryDTO) {
        checkInputForUpdate(categoryDTO);
        checkCategoryUniqueness(categoryDTO);

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            if (categoryDTO.getOwnerId() != 0) {
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
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @CacheEvict("categories")
    public void deleteById(long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Category not found");
            //Because controller method always return 204 http status, include if entity is not found
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @Cacheable("categories")
    public CategoryDTO getById(long id) {
        log.info("getting category by id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category is not found"));
        return categoryMapper.toDTO(category);
    }

    @Cacheable("categoriesList")
    public List<CategoryDTO> getCategories() {
        log.info("getting categories");
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
        if (categoryDTO.getOwnerId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Required ownerId field is empty");
        }
    }

    private void checkInputForUpdate(CategoryDTO categoryDTO) {
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
