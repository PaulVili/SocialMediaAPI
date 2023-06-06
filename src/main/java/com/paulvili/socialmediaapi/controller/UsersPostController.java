package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.exception.ResourceNotFoundException;
import com.paulvili.socialmediaapi.model.UserPostsModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UsersPostRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import com.paulvili.socialmediaapi.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Posts", description = "Users posts ")
@RequestMapping("/api/posts")
public class UsersPostController {
    @Autowired
    private UsersPostRepository usersPostRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Operation(
            summary = "Retrieve last posts for id",
            description = "Get a Posts object by specifying its id. The response is Posts object with id, userId, postHeader, postText, postImage, createdAt, updatedAt, usersByUserId and http status 200 or 404 if userId not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserPostsModel.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("lastPosts/{userId}")
    public ResponseEntity<List<UserPostsModel>> getLastPosts(@PathVariable int userId){
        List<Integer> findAllByUserId = userFollowerRepository.findTargetIdBySourceId(userId);
        if(findAllByUserId == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usersPostRepository.getUserPostsByUserIdAndSorted(findAllByUserId));
    }
    @Operation(
            summary = "Create new post",
            description = "Gets a JSON object with user id and content of post. The response is http status 201 or 404 if user id not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UserPostsModel.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update post by id",
            description = "Gets a JSON object with user id and content of post. The response is http status 200 or 404 if id not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserPostsModel.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @PutMapping("update/{id}")
    public ResponseEntity<UserPostsModel> updatePost(@PathVariable int id, @RequestBody UserPostsModel usersPost){
        UserPostsModel updatePost = usersPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(("post not found with this id" + id)));
        updatePost.setUserId(usersPost.getUserId());
        updatePost.setPostHeader(usersPost.getPostHeader());
        updatePost.setPostText(usersPost.getPostText());
        updatePost.setPostImage(usersPost.getPostImage());
        updatePost.setUpdatedAt(Date.valueOf(LocalDate.now()));

        usersPostRepository.save(updatePost);
        return ResponseEntity.ok(updatePost);
    }
    @Operation(
            summary = "Delete post by id",
            description = "Gets a id of post. Response http status 204 or 404 if id not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserPostsModel.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<UserPostsModel> deletePost(@PathVariable int id){
        UserPostsModel usersPost = usersPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post not found with this id: " + id));
        usersPostRepository.deleteById(usersPost.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
