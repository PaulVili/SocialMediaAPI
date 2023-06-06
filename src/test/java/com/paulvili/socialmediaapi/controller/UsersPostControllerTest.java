package com.paulvili.socialmediaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulvili.socialmediaapi.TestConfig;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UserPostsModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UsersPostRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import com.paulvili.socialmediaapi.service.AuthenticationService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsersPostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UsersPostRepository usersPostRepository;
    @Autowired
    private UsersRepository usersRepository;
    @AfterEach
    public void resetDb() {
        usersPostRepository.deleteAll();
        usersRepository.deleteAll();
    }
    @Test
    void createNewPostShouldReturnHttpStatus201() throws Exception {
        UsersModel newUser = new UsersModel();
        newUser.setEmail("Paviul@gmail.com");
        newUser.setUserName("Paviul");
        newUser.setPassword("Paviul");
        newUser.setRole(Role.User);
        newUser.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser);

        UserPostsModel newPost = new UserPostsModel();
        newPost.setUserId(newUser.getId());
        newPost.setPostHeader("header");
        newPost.setPostText("text text text");
        newPost.setCreatedAt(Date.valueOf(LocalDate.now()));

        mockMvc.perform(post("/api/posts/create")
                .content(objectMapper.writeValueAsString(newPost))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @Test
    void createNewPostShouldReturnHttpStatus404() throws Exception {
        UserPostsModel newPost = new UserPostsModel();
        newPost.setUserId(0);
        newPost.setPostHeader("");
        newPost.setPostText("");
        mockMvc.perform(post("/api/posts/create")
                .content(objectMapper.writeValueAsString(newPost))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
    @Test
    void updatePostShouldReturnHttpStatus200() throws Exception {
        UsersModel newUser = new UsersModel();
        newUser.setEmail("example@gmail.com");
        newUser.setUserName("example");
        newUser.setPassword("example");
        newUser.setRole(Role.User);
        newUser.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser);

        UserPostsModel newPost = new UserPostsModel();
        newPost.setUserId(newUser.getId());
        newPost.setPostHeader("header");
        newPost.setPostText("text text text");
        newPost.setCreatedAt(Date.valueOf(LocalDate.now()));
        usersPostRepository.save(newPost);

        UserPostsModel updatedPost = new UserPostsModel();
        updatedPost.setId(newPost.getId());
        updatedPost.setUserId(newUser.getId());
        updatedPost.setPostHeader("new header");
        updatedPost.setPostText("new text");
        updatedPost.setCreatedAt(newPost.getCreatedAt());
        updatedPost.setUpdatedAt(Date.valueOf(LocalDate.now()));
        mockMvc.perform(put("/api/posts/update/" + String.valueOf(newPost.getId()))
                        .content(objectMapper.writeValueAsString(updatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }
    @Test
    void updatePostShouldReturnHttpStatus404() throws Exception {
        UsersModel newUser = new UsersModel();
        newUser.setEmail("example@gmail.com");
        newUser.setUserName("example");
        newUser.setPassword("example");
        newUser.setRole(Role.User);
        newUser.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser);

        UserPostsModel newPost = new UserPostsModel();
        newPost.setUserId(newUser.getId());
        newPost.setPostHeader("header");
        newPost.setPostText("text text text");
        newPost.setCreatedAt(Date.valueOf(LocalDate.now()));
        usersPostRepository.save(newPost);

        UserPostsModel updatedPost = new UserPostsModel();
        updatedPost.setUserId(newUser.getId());
        updatedPost.setPostHeader("new header");
        updatedPost.setPostText("new text");
        updatedPost.setCreatedAt(newPost.getCreatedAt());
        updatedPost.setUpdatedAt(Date.valueOf(LocalDate.now()));
        mockMvc.perform(put("/api/posts/update/1" )
                        .content(objectMapper.writeValueAsString(updatedPost))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }
    @Test
    void deletePostShouldReturnHttpStatus200() throws Exception {
        UsersModel newUser = new UsersModel();
        newUser.setEmail("example@gmail.com");
        newUser.setUserName("example");
        newUser.setPassword("example");
        newUser.setRole(Role.User);
        newUser.setRegisteredAt(Date.valueOf(LocalDate.now()));
        usersRepository.save(newUser);

        UserPostsModel newPost = new UserPostsModel();
        newPost.setUserId(newUser.getId());
        newPost.setPostHeader("header");
        newPost.setPostText("text text text");
        newPost.setCreatedAt(Date.valueOf(LocalDate.now()));
        usersPostRepository.save(newPost);

        mockMvc.perform(delete("/api/posts/delete/" + String.valueOf(newPost.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

    }
    @Test
    void deletePostShouldReturnHttpStatus404() throws Exception {
        mockMvc.perform(delete("/api/posts/delete/1" )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }


}