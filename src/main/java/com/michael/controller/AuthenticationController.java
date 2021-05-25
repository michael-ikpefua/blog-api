package com.michael.controller;

import com.michael.exception.UserException;
import com.michael.model.User;
import com.michael.request.UserLoginRequest;
import com.michael.request.UserRegisterRequest;
import com.michael.response.UserResponse;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request, HttpSession session) {
        User user = userService.login(request);
        if (user == null) {
            throw new UserException("Check Email or Password");
        }
        session.setAttribute("user_session", user);
        return new ResponseEntity<>(new UserResponse(user, "User Successfully Logged In."), HttpStatus.OK);
    }

}
