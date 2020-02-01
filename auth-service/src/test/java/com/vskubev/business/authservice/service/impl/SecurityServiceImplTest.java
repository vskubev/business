package com.vskubev.business.authservice.service.impl;

import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.repository.UserRepository;
import com.vskubev.business.authservice.service.SecurityService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class SecurityServiceImplTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private Authentication authentication = mock(Authentication.class);
    private SecurityContext securityContext = mock(SecurityContext.class);

    private SecurityService securityService;
    private User userSample1;
    private LocalDateTime time;

    @BeforeEach
    public void init() {
        time = LocalDateTime.now();
        securityService = new SecurityServiceImpl(userRepository);
        userSample1 = new User("First", "Password123321",
                "First user name", "First@mail.test", time, time);
    }

    @Test
    public void getCurrentUserTestOk() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userSample1);

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(userSample1));

        User user = securityService.getCurrentUser();

        Assert.assertEquals(userSample1.getEmail(), user.getEmail());
        Assert.assertEquals(userSample1.getLogin(), user.getLogin());
        Assert.assertEquals(userSample1.getName(), user.getName());
        Assert.assertEquals(userSample1.getCreatedAt(), user.getCreatedAt());
        Assert.assertEquals(userSample1.getUpdatedAt(), user.getUpdatedAt());
        Assert.assertEquals(user.getHashPassword(), user.getHashPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(any());
    }

    @Test
    public void getCurrentUserWithoutAuthenticationTestFail() {
        when(securityContext.getAuthentication()).thenReturn(null);

        assertThatThrownBy(() -> securityService.getCurrentUser())
                .isInstanceOf(AccessDeniedException.class);

        Mockito.verify(userRepository, Mockito.times(0)).findByEmail(any());
    }

    @Test
    public void getCurrentUserTestFail() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(userSample1);

        when(userRepository.findByEmail(any())).thenThrow(AccessDeniedException.class);

        assertThatThrownBy(() -> securityService.getCurrentUser())
                .isInstanceOf(AccessDeniedException.class);

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(any());
    }
}