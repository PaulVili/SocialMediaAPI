package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.model.UserMessageModel;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UserMessageRepository;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Messages", description = "Users messages")
public class UserMessageController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserMessageRepository userMessageRepository;

    @Operation(
            summary = "Request for correspondence",
            description = "Gets source id and target id in http request. The response is http status 201 or http status 404 if target id or source id is not found or 400 if source id = target id.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) })
    })
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
