package com.paulvili.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulvili.socialmediaapi.TestConfig;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserMessageRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserMessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserMessageRepository userMessageRepository;
    @AfterEach
    public void resetDb() {
        userMessageRepository.deleteAll();
        usersRepository.deleteAll();
    }
    @Test
    void getCorrespondenceShouldReturn400()  throws Exception{
        UsersModel newUser1 = new UsersModel();
        newUser1.setEmail("user1@gmail.com");
        newUser1.setUserName("user1");
        newUser1.setPassword("user1Password");
        newUser1.setRole(Role.User);
        newUser1.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser1);

        UsersModel newUser2 = new UsersModel();
        newUser2.setEmail("user2@gmail.com");
        newUser2.setUserName("user2");
        newUser2.setPassword("user2Password");
        newUser2.setRole(Role.User);
        newUser2.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser2);

        mockMvc.perform(post("/api/messages/correspondence/" + newUser1.getId() + "/" + newUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
    @Test
    void getCorrespondenceShouldReturn404() throws Exception {
        UsersModel newUser1 = new UsersModel();
        newUser1.setEmail("user1@gmail.com");
        newUser1.setUserName("user1");
        newUser1.setPassword("user1Password");
        newUser1.setRole(Role.User);
        newUser1.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser1);
        Random random = new Random();
        int rand = random.nextInt(0, 500);
        mockMvc.perform(post("/api/messages/correspondence/" + newUser1.getId() + "/" + rand)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
    @Test
    void getCorrespondenceShouldReturn201()  throws Exception {
        UsersModel newUser1 = new UsersModel();
        newUser1.setEmail("user1@gmail.com");
        newUser1.setUserName("user1");
        newUser1.setPassword("user1Password");
        newUser1.setRole(Role.User);
        newUser1.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser1);

        UsersModel newUser2 = new UsersModel();
        newUser2.setEmail("user2@gmail.com");
        newUser2.setUserName("user2");
        newUser2.setPassword("user2Password");
        newUser2.setRole(Role.User);
        newUser2.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser2);

        mockMvc.perform(post("/api/messages/correspondence/" + newUser1.getId() + "/" + newUser2.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
}