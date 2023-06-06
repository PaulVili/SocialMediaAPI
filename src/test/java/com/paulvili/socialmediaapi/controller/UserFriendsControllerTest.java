package com.paulvili.socialmediaapi.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserFriendsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UsersRepository usersRepository;
    @AfterEach
    public void resetDb() {
        userFollowerRepository.deleteAll();
        userFriendRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void addFriendShouldReturn200() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));
        mockMvc.perform(post("/api/friends/addFriend")
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void addFriendShouldReturn404() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));
        mockMvc.perform(post("/api/friends/addFriend")
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void acceptFriendShouldReturn200() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        userFriendRepository.save(userFriend);
        mockMvc.perform(put("/api/friends/acceptFriendRequest/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    @Test
    void acceptFriendShouldReturn404() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        mockMvc.perform(put("/api/friends/acceptFriendRequest/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void rejectFriendShouldReturn200() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        UserFollowerModel userFollower = new UserFollowerModel();
        userFollower.setSourceId(newUser1.getId());
        userFollower.setTargetId(newUser2.getId());
        userFollower.setCreatedAt(Date.valueOf(LocalDate.now()));

        userFriendRepository.save(userFriend);
        userFollowerRepository.save(userFollower);

        mockMvc.perform(put("/api/friends/rejectFriendRequest/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    @Test
    void rejectFriendShouldReturn404() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        mockMvc.perform(put("/api/friends/rejectFriendRequest/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deleteFriendShouldReturn200() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        UserFollowerModel userFollower = new UserFollowerModel();
        userFollower.setSourceId(newUser1.getId());
        userFollower.setTargetId(newUser2.getId());
        userFollower.setCreatedAt(Date.valueOf(LocalDate.now()));

        userFriendRepository.save(userFriend);
        userFollowerRepository.save(userFollower);

        mockMvc.perform(delete("/api/friends/deleteFriend/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }
    @Test
    void deleteFriendShouldReturn404() throws Exception{
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

        UserFriendModel userFriend = new UserFriendModel();
        userFriend.setSourceId(newUser1.getId());
        userFriend.setTargetId(newUser2.getId());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));

        mockMvc.perform(delete("/api/friends/deleteFriend/" + userFriend.getSourceId() + "/" + userFriend.getTargetId())
                .content(objectMapper.writeValueAsString(userFriend))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}