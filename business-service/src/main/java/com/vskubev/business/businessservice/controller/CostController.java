package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.service.impl.CostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CostDTO> createCost(@Valid @RequestBody CostDTO costDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(costService.create(costDTO));
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("costId") long costId) {
        costService.deleteById(costId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.GET)
    public ResponseEntity<CostDTO> getCost(@PathVariable("costId") long costId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costService.getById(costId));
    }

    @RequestMapping(value = "/costs", method = RequestMethod.GET)
    public ResponseEntity<List<CostDTO>> getAllCosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costService.getAllCosts());
    }

}
