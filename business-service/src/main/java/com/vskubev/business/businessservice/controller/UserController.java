package com.vskubev.business.businessservice.controller;

import com.google.gson.Gson;
import com.vskubev.business.businessservice.map.UserDTO;
import com.vskubev.business.businessservice.service.impl.UserServiceImpl;
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
    private final Gson gson;
    private final UserServiceImpl userService;

    public UserController(Gson gson, UserServiceImpl userService) {
        this.gson = gson;
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Request: Create user: {}", gson.toJson(userDTO));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userDTO));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") long userId, @Valid @RequestBody UserDTO userDTO) {
        log.info("Request: Update user: {}", gson.toJson(userDTO));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(userId, userDTO));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") long userId) {
        log.info("Request: DELETE /users/{}", userId);

        userService.deleteById(userId);
        log.info("Success");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") long userId) {
        log.info("Request: GET /users/{}", userId);
        UserDTO userDTO = userService.getById(userId);
        log.info("Response: {}", userDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers());
    }

}
