package com.michael.controller;

import com.michael.model.User;
import com.michael.response.UserDTO;
import com.michael.request.UserRegisterRequest;
import com.michael.response.UserResponse;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    IUserService userService;


    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest, HttpSession session) {

        User user = userService.register(userRegisterRequest);

        session.setAttribute("user_session", user);

        return new ResponseEntity<>(new UserResponse(user, "User created successfully"), HttpStatus.OK);

    }


//    public ResponseEntity<?> login(@Valid)

}
