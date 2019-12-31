package com.vskubev.business.businessservice.controller;

import com.google.gson.Gson;
import com.vskubev.business.businessservice.map.CostDTO;
import com.vskubev.business.businessservice.service.impl.CostServiceImpl;
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
public class CostController {
    private final Gson gson;
    private final CostServiceImpl costService;

    public CostController(Gson gson, CostServiceImpl costService) {
        this.gson = gson;
        this.costService = costService;
    }

    @RequestMapping(value = "/costs", method = RequestMethod.POST)
    public ResponseEntity<CostDTO> create(@Valid @RequestBody CostDTO costDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(costService.create(costDTO));
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.PUT)
    public ResponseEntity<CostDTO> update(@PathVariable("costId") long costId,
                                              @Valid @RequestBody CostDTO costDTO) {
        log.info("Request: Update cost: {}", gson.toJson(costDTO));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costService.update(costId, costDTO));
    }


    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("costId") long costId) {
        costService.deleteById(costId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(value = "/costs/{costId}", method = RequestMethod.GET)
    public ResponseEntity<CostDTO> getById(@PathVariable("costId") long costId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costService.getById(costId));
    }

    @RequestMapping(value = "/costs", method = RequestMethod.GET)
    public ResponseEntity<List<CostDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costService.getAllCosts());
    }

}
