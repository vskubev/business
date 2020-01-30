package com.vskubev.business.businessservice.repository;

import com.vskubev.business.businessservice.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author skubev
 */
@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {

    List<Cost> findAllByOwnerId(long id);
}
