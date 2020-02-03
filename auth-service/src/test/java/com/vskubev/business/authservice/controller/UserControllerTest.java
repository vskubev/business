package com.vskubev.business.authservice.controller;


import com.vskubev.business.authservice.map.UserDTO;
import com.vskubev.business.authservice.model.User;
import com.vskubev.business.authservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(classes = UserService.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User userSample1;
    private UserDTO userDTOSample1;
    private LocalDateTime time;
    List<UserDTO> userDTOList = new ArrayList<>();

    @BeforeEach
    public void init() {
        time = LocalDateTime.now();
        userSample1 = new User("First", "Password123321",
                "First user name", "First@mail.test", time, time);
        userDTOSample1 = new UserDTO(0, "First", "Password123321",
                "First user name", "First@mail.test", time, time);
    }

    @Test
    public void getAllTestOk() throws Exception {
        userDTOList.add(userDTOSample1);

        given(userService.getUsers()).willReturn(userDTOList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}