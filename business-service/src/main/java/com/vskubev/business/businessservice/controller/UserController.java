package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author skubev
 */
@RestController
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long userId) {
        userService.deleteById(userId);
    }

}