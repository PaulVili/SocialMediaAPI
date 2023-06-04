package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UserFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.paulvili.socialmediaapi.controller.UserFriendsController.getHttpStatusResponseEntityAndDelete;

@RestController
@RequestMapping("api/follow")
public class UserFollowerController {
    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @DeleteMapping("delete/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> deleteFollow(@PathVariable int sourceId, @PathVariable int targetId){
        return getHttpStatusResponseEntityAndDelete(sourceId, targetId, userFriendRepository, userFollowerRepository);
    }
}
