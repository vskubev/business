package com.vskubev.business.businessservice.controller;

import com.vskubev.business.businessservice.map.UserDTO;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author skubev
 */
@RestController
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") long userId) {
        userService.deleteById(userId);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public UserDTO getUser(@PathVariable("userId") long userId) {
        return userService.getById(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

}
