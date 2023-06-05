package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import com.paulvili.socialmediaapi.request.AuthenticationRequest;
import com.paulvili.socialmediaapi.response.AuthenticationResponse;
import com.paulvili.socialmediaapi.request.RegisterRequest;
import com.paulvili.socialmediaapi.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Users authentication or registration ")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UsersRepository usersRepository;
    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile( "^\\s*?(.+)@(.+?)\\s*$", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }
    @Operation(
            summary = "Registration",
            description = "Gets a JSON object with username, email and password. The response is jwt token or http status 400 if email is invalid.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AuthenticationResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        Optional<UsersModel> findEmail = usersRepository.findByEmail(request.getEmail());
        if (!isEmailValid(request.getEmail()) || findEmail.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.register(request));
    }
    @Operation(
            summary = "Authentication",
            description = "Gets a JSON object  with email and password. The response is jwt token or http status 403 if email or password not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = AuthenticationResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema())
            })
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
