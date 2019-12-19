package com.vskubev.business.businessservice.repository;

import com.vskubev.business.businessservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author skubev
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
