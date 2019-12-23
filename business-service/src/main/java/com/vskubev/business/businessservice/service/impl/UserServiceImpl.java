package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.controller.UserDTO;
import com.vskubev.business.businessservice.controller.UserMapper;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.UserRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author skubev
 */
@Service
public class UserServiceImpl implements CrudService<UserDTO> {

    private final UserRepository userRepository;
    private final UserDTO userDTO;
    private final UserMapper userMapper;

    /**
     * Еще есть сомнения по поводу таких конструкторов и их жизнеспособности
     */
    public UserServiceImpl(UserRepository userRepository, UserDTO userDTO, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userDTO = userDTO;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        LocalDateTime localDateTime = LocalDateTime.now();

        /**
         * здесь, вероятно, надо как то создать юзера на основе юезрДТО
         */

        user.setCreatedAt(localDateTime);
        user.setUpdatedAt(localDateTime);
        User user1 = userRepository.saveAndFlush(user);
        return userMapper.toDto(user1);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        return userMapper.toDto(user);
    }

    public List<UserDTO> getUsers() {
        List<User> users = new ArrayList<>();
        List<UserDTO> usersDTO = new ArrayList<>();
        users = userRepository.findAll();

        /**
         * конвертер users to usersDTO
         */
        for (User user: users) {
            usersDTO.add(userMapper.toDto(user));
        }
        return usersDTO;
    }

}
