package com.vskubev.business.authservice.service.impl;

import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.model.UserAwareUserDetails;
import com.vskubev.business.authservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author skubev
 */
@Service
public class AuthDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        User user = userOptional.get();
        return new UserAwareUserDetails(user);
    }
}
