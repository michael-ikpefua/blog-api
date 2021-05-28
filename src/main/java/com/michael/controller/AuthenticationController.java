package com.michael.controller;

import com.michael.exceptions.UserException;
import com.michael.model.User;
import com.michael.request.UserLoginRequest;
import com.michael.request.UserRegisterRequest;
import com.michael.response.UserResponse;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "auth")
public class AuthenticationController {

    @Autowired
    IUserService userService;

    @PostMapping(path = "register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request, HttpSession session) {

        boolean isUserExists = userService.checkIfUserExist(request.getEmail());
        if (isUserExists) {
            throw new UserException("User email already exist. Please Login");
        }
        User user = userService.register(request);
        session.setAttribute("user_session", user);

        return new ResponseEntity<>(new UserResponse("User created successfully", user, null), HttpStatus.OK);

    }

    @GetMapping(path = "login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request, HttpSession session) {

        User user = userService.login(request);
        if (user == null) {
            throw new UserException("Check Email or Password");
        }
        session.setAttribute("user_session", user);
        return new ResponseEntity<>(new UserResponse( "User Successfully Logged In.", user, null), HttpStatus.OK);
    }

}
