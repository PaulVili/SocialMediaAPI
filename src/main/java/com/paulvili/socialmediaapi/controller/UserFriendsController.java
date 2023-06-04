package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.exception.ResourceNotFoundException;
import com.paulvili.socialmediaapi.model.*;
import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UserFriendRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("api/friends")
public class UserFriendsController {
    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("addFriend")
    public ResponseEntity<UserFriendModel> addFriend(@RequestBody UserFriendModel userFriendModel){

        UsersModel findSourceId = usersRepository.findById(userFriendModel.getSourceId()).orElseThrow(()-> new ResourceNotFoundException(("post not found with this id" + userFriendModel.getSourceId())));
        UsersModel findTargetId = usersRepository.findById(userFriendModel.getTargetId()).orElseThrow(()-> new ResourceNotFoundException(("post not found with this id" + userFriendModel.getTargetId())));

        UserFriendModel userFriend = new UserFriendModel();
        UserFollowerModel userFollower = new UserFollowerModel();

        if(findSourceId.equals(findTargetId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userFriend.setSourceId(userFriendModel.getSourceId());
        userFriend.setTargetId(userFriendModel.getTargetId());
        userFriend.setStatus(Status.New);
        userFriend.setType(userFriendModel.getType());
        userFriend.setNotes(userFriendModel.getNotes());
        userFriend.setCreatedAt(Date.valueOf(LocalDate.now()));
        userFriendRepository.save(userFriend);

        userFollower.setSourceId(userFriendModel.getSourceId());
        userFollower.setTargetId(userFriendModel.getTargetId());
        userFollower.setType(userFriendModel.getType());
        userFollower.setCreatedAt(Date.valueOf(LocalDate.now()));
        userFollowerRepository.save(userFollower);

        return new ResponseEntity<>(userFriend, HttpStatus.OK);
    }


    @PutMapping("acceptFriendRequest/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> acceptFriend(@PathVariable int sourceId, @PathVariable int targetId){
        UserFriendModel findUserFriendsId = userFriendRepository.findBySourceIdAndTargetId(sourceId,targetId);
        if (findUserFriendsId != null){
            userFriendRepository.updateStatus(Status.Active, Date.valueOf(LocalDate.now()), sourceId, targetId);
            UserFollowerModel userFollower= new UserFollowerModel();
            userFollower.setSourceId(targetId);
            userFollower.setTargetId(sourceId);
            userFollower.setCreatedAt(Date.valueOf(LocalDate.now()));
            userFollowerRepository.save(userFollower);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("rejectFriendRequest/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> rejectFriend(@PathVariable int sourceId, @PathVariable int targetId){
        UserFriendModel findUserFriendsId = userFriendRepository.findBySourceIdAndTargetId(sourceId,targetId);
        if (findUserFriendsId != null){
            userFriendRepository.updateStatus(Status.Rejected, Date.valueOf(LocalDate.now()), sourceId, targetId);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("deleteFriend/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> deleteFriend(@PathVariable int sourceId, @PathVariable int targetId){
        return getHttpStatusResponseEntityAndDelete(sourceId, targetId, userFriendRepository, userFollowerRepository);
    }

    static ResponseEntity<HttpStatus> getHttpStatusResponseEntityAndDelete(@PathVariable int sourceId, @PathVariable int targetId, UserFriendRepository userFriendRepository, UserFollowerRepository userFollowerRepository) {
        UserFriendModel findUserFriendsId = userFriendRepository.findBySourceIdAndTargetId(sourceId,targetId);
        if (findUserFriendsId != null){
            userFriendRepository.deleteBySourceIdAndTargetId(sourceId, targetId);
            userFollowerRepository.deleteBySourceIdAndTargetId(sourceId,targetId);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
