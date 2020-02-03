package com.vskubev.business.authservice.controller;

import com.google.gson.Gson;
import com.vskubev.business.authservice.MessageSender;
import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RabbitTemplate rabbitTemplate;
    private MessageSender messageSender;
    private String authExchange = "auth-exchange";
    private String authRoutingKey = "auth-routing-key";

    public MessageSender getMessageSender() {
        return messageSender;
    }
    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public UserController(UserService userService,
                          Gson gson,
                          RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.gson = gson;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        /* Sending to Message Queue */
        try {
            messageSender.sendMessage(rabbitTemplate, authExchange, authRoutingKey, gson.toJson(userDTO));
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.create(userDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
