package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.exception.ResourceNotFoundException;
import com.paulvili.socialmediaapi.model.UserPostsModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UsersPostRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class UsersPostController {
    @Autowired
    private UsersPostRepository usersPostRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping()
    public List<UserPostsModel> getPosts(){
        return usersPostRepository.findAll();
    }

    @GetMapping("lastPosts/{userId}")
    public List<UserPostsModel> getLastPosts(@PathVariable int userId){
        List<Integer> findAllByUserId = userFollowerRepository.findTargetIdBySourceId(userId);
        findAllByUserId.stream().map(String::valueOf).collect(Collectors.joining(","));
        return usersPostRepository.getUserPostsByUserIdAndSorted(findAllByUserId);
    }


    @PostMapping("create")
    public ResponseEntity<UserPostsModel> createNewPost(@RequestBody UserPostsModel usersPost){
        UsersModel findAuthorPost = usersRepository.findByUserId(usersPost.getUserId());
        UserPostsModel newPost = new UserPostsModel();
        if (findAuthorPost != null){
            newPost.setUserId(usersPost.getUserId());
            newPost.setPostHeader(usersPost.getPostHeader());
            newPost.setPostText(usersPost.getPostText());
            newPost.setPostImage(usersPost.getPostImage());
            newPost.setCreatedAt(Date.valueOf(LocalDate.now()));
            usersPostRepository.save(newPost);
        }else{
            return new ResponseEntity<>(newPost, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<UserPostsModel> updatePost(@PathVariable int id, @RequestBody UserPostsModel usersPost){
        UserPostsModel updatePost = usersPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("post not found with this id" + id)));
        updatePost.setUserId(usersPost.getUserId());
        updatePost.setPostHeader(usersPost.getPostHeader());
        updatePost.setPostText(usersPost.getPostText());
        updatePost.setPostImage(usersPost.getPostImage());
        updatePost.setUpdatedAt(usersPost.getUpdatedAt());

        usersPostRepository.save(updatePost);
        return ResponseEntity.ok(updatePost);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<UserPostsModel> deletePost(@PathVariable int id){
        UserPostsModel usersPost = usersPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post not found with this id: " + id));
        usersPostRepository.deleteById(usersPost.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
