package com.vskubev.business.authservice.controller;

import com.google.gson.Gson;
import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.service.UserService;
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
public class UserController {

    private final UserService userService;
    private final Gson gson;

    public UserController(UserService userService,
                          Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userDTO));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> update(@PathVariable("userId") long userId,
                                          @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(userId, userDTO));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("userId") long userId) {
        userService.deleteById(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") long userId) {
        UserDTO userDTO = userService.getById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers());
    }

    @RequestMapping(value = "/users/current", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getCurrentUser());
    }
}
