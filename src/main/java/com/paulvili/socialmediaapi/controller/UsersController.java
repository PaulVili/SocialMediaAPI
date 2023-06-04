package com.paulvili.socialmediaapi.controller;

import com.paulvili.socialmediaapi.exception.ResourceNotFoundException;
import com.paulvili.socialmediaapi.model.Role;
import com.paulvili.socialmediaapi.model.UsersModel;
import com.paulvili.socialmediaapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("create")
    public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel users){
        UsersModel user = usersRepository.findByUserName(users.getUserName());
        if (user == null){
            user = new UsersModel();
            user.setUserName(users.getUserName());
            user.setEmail(users.getEmail());
            user.setPasswordHash(bCryptPasswordEncoder.encode(users.getPasswordHash()));
            user.setRole(Role.User);
            user.setRegisteredAt(Date.valueOf(LocalDate.now()));
            usersRepository.save(user);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
