package com.vskubev.business.authservice.service.impl;

import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.map.UserMapper;
import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.repository.UserRepository;
import com.vskubev.business.authservice.service.SecurityService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private UserMapper userMapper = mock(UserMapper.class);
    //    private UserMapper userMapper = new UserMapper();
    private SecurityService securityService = mock(SecurityServiceImpl.class);

    private UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper, securityService);

    private final LocalDateTime time = LocalDateTime.now();
    private final User userSample1 = new User("First", "Password123321",
            "First user name", "First@mail.test", time, time);
    private final User userSample2 = new User("Second", "Password123321",
            "Second user name", "Second@mail.test", time, time);
    private final UserDTO userDTOSample = new UserDTO(0, "First", "Password123321",
            "First user name", "First@mail.test", time, time);

    @Test
    public void createUserTest() {
        when(userRepository.save(any(User.class)))
                .thenReturn(userSample1);

        UserDTO userDTO = userService.create(userDTOSample);

        Assert.assertEquals(userDTOSample, userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void getUsersTest() {
        List<User> usersSample = new ArrayList<>();
        usersSample.add(userSample1);
        usersSample.add(userSample2);

        List<UserDTO> usersSampleDTO = usersSample.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        when(userRepository.findAll()).thenReturn(usersSample);

        List<UserDTO> users = userService.getUsers();

        Assert.assertEquals(usersSampleDTO, users);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

//    @Test
//    public void getUsersTestException() {
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            Integer.parseInt("1a");
//        });
//
//        when(userRepository.findAll()).thenThrow(new RuntimeException());
//
//        List<UserDTO> users = userService.getUsers();
//
//        String expectedMessage = "Runtime";
//        String actualMessage = exception.getMessage();
//
//        Mockito.verify(userRepository, Mockito.doThrow())
//    }
}