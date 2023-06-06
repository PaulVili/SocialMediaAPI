package com.paulvili.socialmediaapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulvili.socialmediaapi.TestConfig;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import com.paulvili.socialmediaapi.request.AuthenticationRequest;
import com.paulvili.socialmediaapi.request.RegisterRequest;
import com.paulvili.socialmediaapi.service.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void registerShouldReturn201() throws Exception {
        usersRepository.deleteAll();
        RegisterRequest newUser = new RegisterRequest();
        newUser.setUserName("pail");
        newUser.setEmail("pail@gmail.com");
        newUser.setPassword(service.passwordEncoder.encode("password"));
        mockMvc.perform(post("/api/auth/register")
                .content(objectMapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
        usersRepository.deleteAll();
    }

    @Test
    void registerShouldReturn400() throws Exception {
        RegisterRequest newUser = new RegisterRequest();
        newUser.setUserName("pail");
        newUser.setEmail("pailgmail.com");
        newUser.setPassword(service.passwordEncoder.encode("password"));
        mockMvc.perform(post("/api/auth/register")
                .content(objectMapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
    @Test
    void authenticateShouldReturn200() throws Exception {
        usersRepository.deleteAll();
        UsersModel newUser = new UsersModel();
        newUser.setEmail("Paviul@gmail.com");
        newUser.setUserName("Paviul");
        newUser.setPassword(service.passwordEncoder.encode("Paviul"));
        newUser.setRole(Role.User);
        newUser.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser);
        AuthenticationRequest User = new AuthenticationRequest();
        User.setEmail("Paviul@gmail.com");
        User.setPassword("Paviul");
        mockMvc.perform(post("/api/auth/authenticate")
                .content(objectMapper.writeValueAsString(User))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void authenticateShouldReturn401() throws Exception {
        AuthenticationRequest newUser = new AuthenticationRequest();
        newUser.setEmail("pailgmail.com");
        newUser.setPassword(service.passwordEncoder.encode("password"));
        mockMvc.perform(post("/api/auth/authenticate")
                .content(objectMapper.writeValueAsString(newUser))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
    }
}