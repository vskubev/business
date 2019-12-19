package com.vskubev.business.businessservice.repository;

import com.vskubev.business.businessservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author skubev
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
