package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.UserRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements CrudService<User> {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
