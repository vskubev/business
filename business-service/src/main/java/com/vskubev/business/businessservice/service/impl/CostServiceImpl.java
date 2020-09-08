package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.client.UserServiceClient;
import com.vskubev.business.businessservice.map.CategoryMapper;
import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.map.CostMapper;
import com.vskubev.business.businessservice.map.UserDTO;
import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.repository.CostRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
@Slf4j
public class CostServiceImpl implements CrudService<CostDTO> {

    @Autowired
    private HttpServletRequest request;
    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;
    private final UserServiceClient userServiceClient;

    public CostServiceImpl(CostRepository costRepository,
                           CostMapper costMapper,
                           CategoryServiceImpl categoryService,
                           CategoryMapper categoryMapper,
                           UserServiceClient userServiceClient) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.userServiceClient = userServiceClient;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public CostDTO create(CostDTO costDTO) {
        String token = request.getHeader("Authorization");
        checkInput(costDTO);

        if (!userServiceClient.getUserById(costDTO.getOwnerId(), token).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found");
        }

        Cost cost = costMapper.toEntity(costDTO);

        LocalDateTime localDateTime = LocalDateTime.now();

        cost.setCreatedAt(localDateTime);
        cost.setUpdatedAt(localDateTime);

        return costMapper.toDTO(costRepository.save(cost));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public CostDTO update(long id, CostDTO costDTO) {
        Optional<Cost> cost = costRepository.findById(id);

        if (cost.isPresent()) {
            if (costDTO.getPrice() != null) {
                cost.get().setPrice(costDTO.getPrice());
            }
            if (costDTO.getCategoryId() != 0) {
                Category category = categoryMapper.toEntity(categoryService.getById(costDTO.getCategoryId()));
                cost.get().setCategory(category);
            }
            cost.get().setUpdatedAt(LocalDateTime.now());
            return costMapper.toDTO(costRepository.save(cost.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost not found");
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @CacheEvict("costs")
    public void deleteById(long id) {
        try {
            costRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Cost not found");
            //Because controller method always return 204 http status, include if entity is not found
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @Cacheable("costs")
    public CostDTO getById(long id) {
        Cost cost = costRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost is not found"));
        return costMapper.toDTO(cost);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @Cacheable("userCostsList")
    public List<CostDTO> getAllCostsUser(String token) {
        UserDTO userDTO = userServiceClient.getCurrentUser(token).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found"));
        long id = userDTO.getId();
        return costRepository.findAllByOwnerId(id).stream()
                .map(costMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable("costsList")
    public List<CostDTO> getAllCosts() {
        return costRepository.findAll().stream()
                .map(costMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void checkInput(CostDTO costDTO) {
        if (costDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (costDTO.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Required price field is empty");
        }
        if (costDTO.getOwnerId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Required ownerId field is empty");
        }
        if (costDTO.getCategoryId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Required categoryId field is empty");
        }
    }

}
