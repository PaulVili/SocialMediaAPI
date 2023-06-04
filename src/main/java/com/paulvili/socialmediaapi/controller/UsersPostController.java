package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.exception.ResourceNotFoundException;
import com.paulvili.socialmediaapi.model.UserPostsModel;
import com.paulvili.socialmediaapi.repository.UsersPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class UsersPostController {
    @Autowired
    private UsersPostRepository usersPostRepository;

    @GetMapping()
    public List<UserPostsModel> getPosts(){
        return usersPostRepository.findAll();
    }



    @PostMapping("create")
    public ResponseEntity<UserPostsModel> createNewPost(@RequestBody UserPostsModel usersPost){
        usersPostRepository.save(usersPost);
        return new ResponseEntity<>(usersPost, HttpStatus.CREATED);
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
