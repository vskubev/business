package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.map.CategoryMapper;
import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.map.CostMapper;
import com.vskubev.business.businessservice.model.Category;
import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.repository.CostRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
public class CostServiceImpl implements CrudService<CostDTO> {

    private final CostRepository costRepository;
    private final CostMapper costMapper;
    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper,
                           CategoryServiceImpl categoryService, CategoryMapper categoryMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CostDTO create(CostDTO costDTO) {
        checkInput(costDTO);

        Cost cost = costMapper.toEntity(costDTO);

        LocalDateTime localDateTime = LocalDateTime.now();

        cost.setCreatedAt(localDateTime);
        cost.setUpdatedAt(localDateTime);

        return costMapper.toDTO(costRepository.save(cost));
    }

    @Override
    @Transactional
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        costRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT));
        costRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CostDTO getById(long id) {
        Cost cost = costRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost is not found"));
        return costMapper.toDTO(cost);
    }

    @Transactional
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
