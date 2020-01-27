package com.vskubev.business.authservice.service.impl;

import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.map.UserMapper;
import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.repository.UserRepository;
import com.vskubev.business.authservice.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author skubev
 */
@Service
@Slf4j
public class UserServiceImpl implements CrudService<UserDTO> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityServiceImpl securityService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           SecurityServiceImpl securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.securityService = securityService;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        checkInput(userDTO);
        checkUserUniqueness(userDTO);

        User user = userMapper.toEntity(userDTO);
        user.setHashPassword(new BCryptPasswordEncoder().encode(user.getHashPassword()));

        LocalDateTime localDateTime = LocalDateTime.now();
        user.setCreatedAt(localDateTime);
        user.setUpdatedAt(localDateTime);

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public UserDTO update(long id, UserDTO userDTO) {
        checkInputForUpdate(userDTO);
        checkUserUniqueness(userDTO);

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            if (userDTO.getLogin() != null) {
                user.get().setLogin(userDTO.getLogin());
            }
            if (userDTO.getPassword() != null) {
                user.get().setHashPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            }
            if (userDTO.getName() != null) {
                user.get().setName(userDTO.getName());
            }
            if (userDTO.getEmail() != null) {
                user.get().setEmail(userDTO.getEmail());
            }
            user.get().setUpdatedAt(LocalDateTime.now());
            return userMapper.toDTO(userRepository.save(user.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void deleteById(long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("User is not found");
            //Because controller method always return 204 http status, include if entity is not found
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public UserDTO getById(long id) {
        User currentUser = securityService.getCurrentUser();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not found"));
        if (currentUser.getId() == user.getId()) {
            return userMapper.toDTO(user);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public UserDTO getCurrentUser() {
        return userMapper.toDTO(securityService.getCurrentUser());
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
                    "At least one upper case English letter, one lower case English letter, " +
                    "one digit, minimum eight in length");
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

    private void checkInputForUpdate(UserDTO userDTO) {
        if (!(userDTO.getLogin() == null)
                && !userDTO.getLogin().matches("^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is incorrect");
        }

        if (!(userDTO.getPassword() == null)
                && !userDTO.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect. " +
                    "At least one upper case English letter, one lower case English letter, one digit, " +
                    "minimum eight in length");
        }

        if (!(userDTO.getName() == null)
                && !userDTO.getName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is incorrect");
        }

        if (!(userDTO.getEmail() == null)
                && !userDTO.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is incorrect");
        }
    }

    private void checkUserUniqueness(@NotNull UserDTO userDTO) {
        checkLoginUniqueness(userDTO.getLogin());
        checkEmailUniqueness(userDTO.getEmail());
    }

    private void checkLoginUniqueness(@NotNull String login) {
        if (userRepository.findByLogin(login).isPresent()) {
            String error = String.format("The user login %s already exists", login);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private void checkEmailUniqueness(@NotNull String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            String error = String.format("The user email %s already exists", email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

}
