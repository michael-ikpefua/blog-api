package com.michael.controller;

import com.michael.model.User;
import com.michael.request.UserRegisterRequest;
import com.michael.response.UserResponse;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "auth")
public class AuthenticationController {

    @Autowired
    IUserService userService;

    @RequestMapping(path = "register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request, HttpSession session) {

        User user = userService.register(request);

        session.setAttribute("user_session", user);

        return new ResponseEntity<>(new UserResponse(user, "User created successfully"), HttpStatus.OK);

    }


//    public ResponseEntity<?> login(@Valid)

}
