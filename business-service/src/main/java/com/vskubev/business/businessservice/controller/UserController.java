package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
import javassist.NotFoundException;
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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public User getUser(long id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @RequestMapping(value = "/users/delete", method = RequestMethod.DELETE)
    public void deleteUser(long id) {
        userService.deleteById(id);
    }


}
