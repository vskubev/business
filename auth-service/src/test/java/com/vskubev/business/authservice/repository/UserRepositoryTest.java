package com.vskubev.business.authservice.repository;

import com.vskubev.business.authservice.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User userSample1;
    private LocalDateTime time;

    @BeforeEach
    public void init() {
        time = LocalDateTime.now();
        userSample1 = new User("First", "Password123321",
                "First user name", "First@mail.test", time, time);
    }

    @Test
    public void findByLogin() {
        userRepository.save(userSample1);

        Optional<User> user = userRepository.findByEmail("First@mail.test");

        Assert.assertEquals(userSample1, user.get());
    }
}