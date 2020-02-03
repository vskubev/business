package com.vskubev.business.authservice.repository;

import com.vskubev.business.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author skubev
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String name);

    Optional<User> findByEmail(String name);
}
