package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.repository.CostRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * @author skubev
 */
@Service
public class CostServiceImpl implements CrudService<Cost> {

    private final CostRepository costRepository;

    public CostServiceImpl(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    @Override
    public Cost create(Cost entity) {
        LocalDateTime localDateTime = LocalDateTime.now();
        entity.setCreatedAt(localDateTime);
        entity.setUpdatedAt(localDateTime);

        return costRepository.saveAndFlush(entity);
    }

    @Override
    public void deleteById(long id) {
        costRepository.deleteById(id);
    }

    @Override
    public Cost getById(long id) {
        return costRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
