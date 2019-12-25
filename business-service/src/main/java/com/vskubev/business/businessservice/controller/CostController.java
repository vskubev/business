package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.service.impl.CostServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author skubev
 */
@RestController
public class CostController {

    private final CostServiceImpl costService;

    public CostController(CostServiceImpl costService) {
        this.costService = costService;
    }

    @RequestMapping(value = "/costs", method = RequestMethod.POST)
    public CostDTO createCost(@Valid @RequestBody CostDTO costDTO) {
        return costService.create(costDTO);
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("costId") long costId) {
        costService.deleteById(costId);
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.GET)
    public CostDTO getCost(@PathVariable("costId") long costId) {
        return costService.getById(costId);
    }

    @RequestMapping(value = "/costs", method = RequestMethod.GET)
    public List<CostDTO> getAllCosts() {
        return costService.getAllCosts();
    }

}
