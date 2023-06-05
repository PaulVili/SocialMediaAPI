package com.paulvili.socialmediaapi.service;

import com.paulvili.socialmediaapi.request.AuthenticationRequest;
import com.paulvili.socialmediaapi.response.AuthenticationResponse;
import com.paulvili.socialmediaapi.request.RegisterRequest;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UsersRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = UsersModel.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .registeredAt(Date.valueOf(LocalDate.now()))
                .build();
        repository.save(user);
        return responseBuilder(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
                ));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        return responseBuilder(user);
    }

    private AuthenticationResponse responseBuilder(UsersModel user){
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
