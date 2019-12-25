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
import java.util.List;
import java.util.stream.Collectors;

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
        checkUserExists(userDTO);

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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        return userMapper.toDto(user);
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    private void checkInput(UserDTO userDTO) {
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (userDTO.getLogin() == null
                || userDTO.getLogin().isEmpty()
                || !userDTO.getLogin().matches("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is incorrect");
        }

        if (userDTO.getPassword() == null
                || userDTO.getPassword().isEmpty()
                || !userDTO.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect. " +
                    "At least one upper case English letter, one lower case English letter, one digit, minimum eight in length");
        }

        if (userDTO.getName() == null
                || userDTO.getName().isEmpty()
                || !userDTO.getName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is incorrect");
        }

        if (userDTO.getEmail() == null
                || userDTO.getEmail().isEmpty()
                || !userDTO.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is incorrect");
        }
    }

    private void checkUserExists(UserDTO userDTO) {
        if (!UniqueUsernameValidator(userDTO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this login is already exists");
        }

        if (!UniqueEmailValidator(userDTO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email is already exists");
        }
    }

    private boolean UniqueUsernameValidator(UserDTO userDTO) {
        String login = userDTO.getLogin();

        for (UserDTO user: getUsers()) {
            if (user.getLogin().equals(login)) return false;
        }
        return true;
    }

    private boolean UniqueEmailValidator(UserDTO userDTO) {
        String email = userDTO.getEmail();

        for (UserDTO user: getUsers()) {
            if (user.getEmail().equals(email)) return false;
        }
        return true;
    }

}
