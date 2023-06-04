package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.model.UserFriendModel;
import com.paulvili.socialmediaapi.model.UserMessageModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserMessageRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/messages")
public class UserMessageController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserMessageRepository userMessageRepository;

    @PostMapping("correspondence/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> getCorrespondence(@PathVariable int sourceId, @PathVariable int targetId){
        Optional<UsersModel> findSourceId = usersRepository.findById(sourceId);
        Optional<UsersModel> findTargetId = usersRepository.findById(targetId);

        UserMessageModel userMessage = new UserMessageModel();

        if(findSourceId.equals(findTargetId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (findSourceId.isEmpty() || findTargetId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userMessage.setSourceId(sourceId);
        userMessage.setTargetId(targetId);
        userMessageRepository.save(userMessage);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
