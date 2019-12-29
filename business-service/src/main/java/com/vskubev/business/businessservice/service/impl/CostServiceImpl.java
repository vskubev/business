package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.map.CostMapper;
import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.repository.CostRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
public class CostServiceImpl implements CrudService<CostDTO> {

    private final CostRepository costRepository;
    private final CostMapper costMapper;

    public CostServiceImpl(CostRepository costRepository, CostMapper costMapper) {
        this.costRepository = costRepository;
        this.costMapper = costMapper;
    }

    @Override
    public CostDTO create(CostDTO costDTO) {
        checkInput(costDTO);

        Cost cost = costMapper.toEntity(costDTO);

        LocalDateTime localDateTime = LocalDateTime.now();

        cost.setCreatedAt(localDateTime);
        cost.setUpdatedAt(localDateTime);

        return costMapper.toDto(costRepository.save(cost));
    }

    @Override
    public void deleteById(long id) {
        costRepository.deleteById(id);
    }

    @Override
    public CostDTO getById(long id) {
        Cost cost = costRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost is not found"));
        return costMapper.toDto(cost);
    }

    @Override
    public CostDTO update(long id, CostDTO costDTO) {
        return null;
    }

    public List<CostDTO> getAllCosts() {
        return costRepository.findAll().stream()
                .map(costMapper::toDto)
                .collect(Collectors.toList());
    }

    private void checkInput(CostDTO costDTO) {
        if (costDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
