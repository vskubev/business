package com.vskubev.business.businessservice.service.impl;

import com.vskubev.business.businessservice.map.UserDTO;
import com.vskubev.business.businessservice.map.UserMapper;
import com.vskubev.business.businessservice.model.User;
import com.vskubev.business.businessservice.repository.UserRepository;
import com.vskubev.business.businessservice.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skubev
 */
@Service
public class UserServiceImpl implements CrudService<UserDTO> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        checkInput(userDTO);

        User user = userMapper.toEntity(userDTO);

        LocalDateTime localDateTime = LocalDateTime.now();
        user.setCreatedAt(localDateTime);
        user.setUpdatedAt(localDateTime);

        return userMapper.toDto(userRepository.saveAndFlush(user));
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

        /*
         * конвертер users to usersDTO
         */
        for (User user: users) {
            usersDTO.add(userMapper.toDto(user));
        }
        return usersDTO;
    }

    private void checkInput(UserDTO userDTO) {
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (userDTO.getEmail() == null
                || userDTO.getEmail().isEmpty()
                || !userDTO.getEmail().matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$\n")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email is incorrect");
        }
        /**
         * .... продолжи сам
         */
    }
}
