package com.vskubev.business.authservice.controller;


import com.google.gson.Gson;
import com.vskubev.business.authservice.map.UserDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private Gson gson;

    private UserDTO userDTOSample1;
    List<UserDTO> userDTOList = new ArrayList<>();

    @BeforeEach
    public void init() {
        userDTOSample1 = new UserDTO(0, "First", "Password123321",
                "First user name", "First@mail.test", null, null);
    }

    @Test
    public void getAllTestOk() throws Exception {
        userDTOList.add(userDTOSample1);

        given(userService.getUsers()).willReturn(userDTOList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createUserTestOk() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(userDTOSample1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void deleteTestOk() throws Exception {
        long userId = 1;
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());
    }
}