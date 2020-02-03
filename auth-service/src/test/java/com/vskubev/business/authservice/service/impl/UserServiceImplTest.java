package com.vskubev.business.authservice.service.impl;

import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.map.UserMapper;
import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.repository.UserRepository;
import com.vskubev.business.authservice.service.SecurityService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class UserServiceImplTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private SecurityService securityService = mock(SecurityServiceImpl.class);
    private UserMapper userMapper = new UserMapper();

    private UserServiceImpl userService;
    private User userSample1;
    private User userSample2;
    private UserDTO userDTOSample1;
    private UserDTO userDTOSample2;
    private LocalDateTime time;

    @BeforeEach
    public void init() {
        time = LocalDateTime.now();
        userService = new UserServiceImpl(userRepository, userMapper, securityService, template);
        userSample1 = new User("First", "Password123321",
                "First user name", "First@mail.test", time, time);
        userSample2 = new User("Second", "Password123321",
                "Second user name", "Second@mail.test", time, time);
        userDTOSample1 = new UserDTO(0, "First", "Password123321",
                "First user name", "First@mail.test", time, time);
        userDTOSample2 = new UserDTO(0, "Second", "Password123321",
                "Second user name", "Second@mail.test", time, time);
    }

    @Test
    public void createUserTestOk() {
        when(userRepository.save(any(User.class)))
                .thenReturn(userSample1);

        UserDTO userDTO = userService.create(userDTOSample1);

        Assert.assertEquals(userDTOSample1.getId(), userDTO.getId());
        Assert.assertEquals(userDTOSample1.getEmail(), userDTO.getEmail());
        Assert.assertEquals(userDTOSample1.getLogin(), userDTO.getLogin());
        Assert.assertEquals(userDTOSample1.getName(), userDTO.getName());
        Assert.assertEquals(userDTOSample1.getCreatedAt(), userDTO.getCreatedAt());
        Assert.assertEquals(userDTOSample1.getUpdatedAt(), userDTO.getUpdatedAt());
        Assert.assertTrue(userDTO.getPassword().isEmpty());
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void updateUserTestOk() {
        long userId = 1;
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(userSample1));
        when(userRepository.save(any(User.class)))
                .thenReturn(userSample1);

        UserDTO userDTO = userService.update(userId, userDTOSample2);

        Assert.assertEquals(userDTOSample2.getId(), userDTO.getId());
        Assert.assertEquals(userDTOSample2.getEmail(), userDTO.getEmail());
        Assert.assertEquals(userDTOSample2.getLogin(), userDTO.getLogin());
        Assert.assertEquals(userDTOSample2.getName(), userDTO.getName());
        Assert.assertEquals(userDTOSample2.getCreatedAt(), userDTO.getCreatedAt());
        Assert.assertNotEquals(userDTOSample2.getUpdatedAt(), userDTO.getUpdatedAt());
        Assert.assertTrue(userDTO.getPassword().isEmpty());
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void updateUserTestFail() {
        long userId = 1;
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(userId, userDTOSample1))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("404 NOT_FOUND");

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void deleteByIdTestOk() {
        long userId = 1;
        userService.deleteById(userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void deleteByIdTestFail() {
        long userId = 1;

        Mockito.doThrow(new RuntimeException()).when(userRepository).deleteById(anyLong());

        assertThatThrownBy(() -> userService.deleteById(userId))
                .isInstanceOf(RuntimeException.class);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void getByIdTestOk() {
        long userId = 0;
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(userSample1));

        when(securityService.getCurrentUser())
                .thenReturn(userSample1);

        UserDTO userDTO = userService.getById(userId);

        Assert.assertEquals(userDTOSample1.getId(), userDTO.getId());
        Assert.assertEquals(userDTOSample1.getEmail(), userDTO.getEmail());
        Assert.assertEquals(userDTOSample1.getLogin(), userDTO.getLogin());
        Assert.assertEquals(userDTOSample1.getName(), userDTO.getName());
        Assert.assertEquals(userDTOSample1.getCreatedAt(), userDTO.getCreatedAt());
        Assert.assertEquals(userDTOSample1.getUpdatedAt(), userDTO.getUpdatedAt());
        Assert.assertTrue(userDTO.getPassword().isEmpty());

        Mockito.verify(securityService, Mockito.times(1)).getCurrentUser();
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void getByIdTestFail() {
        long userId = 1;
        when(userRepository.findById(anyLong()))
                .thenThrow(ResponseStatusException.class);

        assertThatThrownBy(() -> userService.getById(userId))
                .isInstanceOf(ResponseStatusException.class);

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void getByIdTestFail2() {
        long userId = 1;
        User user = mock(User.class);

        when(securityService.getCurrentUser())
                .thenReturn(userSample1);
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(userSample2));
        when(user.getId())
                .thenReturn((long) 10);

        assertThatThrownBy(() -> userService.getById(userId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("403 FORBIDDEN");

        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    public void getCurrentUserOk() {
        when(securityService.getCurrentUser())
                .thenReturn(userSample1);

        UserDTO userDTO = userService.getCurrentUser();

        Assert.assertEquals(userDTOSample1.getId(), userDTO.getId());
        Assert.assertEquals(userDTOSample1.getEmail(), userDTO.getEmail());
        Assert.assertEquals(userDTOSample1.getLogin(), userDTO.getLogin());
        Assert.assertEquals(userDTOSample1.getName(), userDTO.getName());
        Assert.assertEquals(userDTOSample1.getCreatedAt(), userDTO.getCreatedAt());
        Assert.assertEquals(userDTOSample1.getUpdatedAt(), userDTO.getUpdatedAt());
        Assert.assertTrue(userDTO.getPassword().isEmpty());
        Mockito.verify(securityService, Mockito.times(1)).getCurrentUser();

        Mockito.verify(securityService, Mockito.times(1)).getCurrentUser();
    }

    @Test
    public void getCurrentUserFail() {
        when(securityService.getCurrentUser())
                .thenThrow(AccessDeniedException.class);

        assertThatThrownBy(() -> userService.getCurrentUser())
                .isInstanceOf(AccessDeniedException.class);

        Mockito.verify(securityService, Mockito.times(1)).getCurrentUser();
    }

    @Test
    public void getUsersTestOk() {
        List<User> usersSample = new ArrayList<>();
        usersSample.add(userSample1);
        usersSample.add(userSample2);

        List<UserDTO> usersSampleDTO = usersSample.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        when(userRepository.findAll()).
                thenReturn(usersSample);

        List<UserDTO> users = userService.getUsers();

        Assert.assertEquals(usersSampleDTO, users);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUsersTestFail() throws Exception {
        when(userRepository.findAll())
                .thenThrow(new RuntimeException());

        assertThatThrownBy(() -> userService.getUsers())
                .isInstanceOf(RuntimeException.class);

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }
}