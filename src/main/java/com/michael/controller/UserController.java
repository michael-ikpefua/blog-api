package com.michael.controller;

import com.michael.model.User;
import com.michael.response.MessageResponse;
import com.michael.response.UserResponse;
import com.michael.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<?> index() {
        List<User> users = userService.getAllUser();
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage("List of Users");
        userResponse.setUsers(users);

        return  new ResponseEntity<>(userResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> destroy(@PathVariable Long userId) {
       User user =  userService.getUserById(userId);

       userService.removeUser(user);

       return new ResponseEntity(new MessageResponse("User Deleted"), new HttpHeaders(), HttpStatus.OK);
    }
}
