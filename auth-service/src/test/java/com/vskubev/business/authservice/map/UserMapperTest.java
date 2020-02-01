package com.vskubev.business.authservice.map;

import com.vskubev.business.authservice.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
class UserMapperTest {

    private UserMapper userMapper = new UserMapper();

    private User userSample1;
    private UserDTO userDTOSample1;
    private LocalDateTime time;

    @BeforeEach
    public void init() {
        time = LocalDateTime.now();
        userSample1 = new User("First", "Password123321",
                "First user name", "First@mail.test", time, time);
        userDTOSample1 = new UserDTO(0, "First", "Password123321",
                "First user name", "First@mail.test", time, time);
    }

    @Test
    public void toDTOTestOk() {
        UserDTO userDTO = userMapper.toDTO(userSample1);

        Assert.assertEquals(userDTOSample1.getLogin(), userDTO.getLogin());
        Assert.assertEquals(userDTOSample1.getName(), userDTO.getName());
        Assert.assertEquals(userDTOSample1.getEmail(), userDTO.getEmail());
        Assert.assertEquals(userDTOSample1.getCreatedAt(), userDTO.getCreatedAt());
        Assert.assertEquals(userDTOSample1.getUpdatedAt(), userDTO.getUpdatedAt());
        Assert.assertTrue(userDTO.getPassword().isEmpty());
    }

    @Test
    public void toEntityTestOk() {
        User user = userMapper.toEntity(userDTOSample1);

        Assert.assertEquals(userSample1.getLogin(), user.getLogin());
        Assert.assertEquals(userSample1.getHashPassword(), user.getHashPassword());
        Assert.assertEquals(userSample1.getName(), user.getName());
        Assert.assertEquals(userSample1.getEmail(), user.getEmail());
        Assert.assertEquals(userSample1.getCreatedAt(), user.getCreatedAt());
        Assert.assertEquals(userSample1.getUpdatedAt(), user.getUpdatedAt());
    }
}