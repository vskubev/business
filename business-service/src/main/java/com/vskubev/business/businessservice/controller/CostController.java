package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.Cost;
import com.vskubev.business.businessservice.service.impl.CostServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Cost createCost(@Valid @RequestBody Cost cost) {
        return costService.create(cost);
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("costId") long costId) {
        costService.deleteById(costId);
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.GET)
    public Cost getCost(@PathVariable("costId") long costId) {
        return costService.getById(costId);
    }

}
