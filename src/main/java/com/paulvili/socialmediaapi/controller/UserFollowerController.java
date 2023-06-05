package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.repository.UserFollowerRepository;
import com.paulvili.socialmediaapi.repository.UserFriendRepository;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.paulvili.socialmediaapi.controller.UserFriendsController.getHttpStatusResponseEntityAndDelete;

@RestController
@RequestMapping("api/follow")
@Tag(name = "Followers", description = "Users followers")
public class UserFollowerController {
    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserFollowerRepository userFollowerRepository;

    @Operation(
            summary = "Delete follow",
            description = "Gets source id and target id in http request. The response is http status 204 or 404 if target id or source id is not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("delete/{sourceId}/{targetId}")
    public ResponseEntity<HttpStatus> deleteFollow(@PathVariable int sourceId, @PathVariable int targetId){
        return getHttpStatusResponseEntityAndDelete(sourceId, targetId, userFriendRepository, userFollowerRepository);
    }
}
