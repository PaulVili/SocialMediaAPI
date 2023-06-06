package com.paulvili.socialmediaapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulvili.socialmediaapi.TestConfig;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UserFollowerModel;
import com.paulvili.socialmediaapi.model.UserFriendModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UserFriendRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserFollowerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserFriendRepository userFriendRepository;
    @AfterEach
    public void resetDb() {
        userFollowerRepository.deleteAll();
        usersRepository.deleteAll();
    }
    @Test
    void deleteFollowShouldReturn204() throws Exception {
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
        UserFriendModel deletedFriends = new UserFriendModel();
        deletedFriends.setSourceId(newUser1.getId());
        deletedFriends.setTargetId(newUser2.getId());
        deletedFriends.setCreatedAt(Date.valueOf(LocalDate.now()));
        userFriendRepository.save(deletedFriends);

        UserFollowerModel deletedFollow = new UserFollowerModel();
        deletedFollow.setSourceId(newUser1.getId());
        deletedFollow.setTargetId(newUser2.getId());
        deletedFollow.setCreatedAt(Date.valueOf(LocalDate.now()));
        userFollowerRepository.save(deletedFollow);
        mockMvc.perform(delete("/api/follow/delete/" + deletedFollow.getSourceId() + "/" + deletedFollow.getTargetId())
        ).andExpect(status().isNoContent());
    }
    @Test
    void deleteFollowShouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/follow/delete/54648466/156465646")
        ).andExpect(status().isNotFound());
    }
}