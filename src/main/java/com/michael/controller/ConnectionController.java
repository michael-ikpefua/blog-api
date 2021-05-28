package com.michael.controller;

import com.michael.exceptions.ConnectionException;
import com.michael.exceptions.UserException;
import com.michael.model.Connection;
import com.michael.model.User;
import com.michael.repository.UserRepository;
import com.michael.response.MessageResponse;
import com.michael.service.contracts.IConnectionService;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "connections")
public class ConnectionController extends BaseController{

    @Autowired
    IConnectionService connectionService;

    @Autowired
    IUserService userService;

    @PostMapping(path = "{connectionId}/add-connection")
    public ResponseEntity<?> store(@PathVariable Long connectionId, HttpSession session) {
        User owner = authUser(session);
        if (owner == null) throw new UserException("Please login, before you can add user to connection");

        User connection = userService.getUserById(connectionId);
        if (connection == null) throw new UserException("User account Not Found!!!");

        if (owner.getId().equals(connection.getId())) {
            System.err.println(owner.getId() + " connection id " + connection.getId());
            throw new UserException("I cannot add myself to my connection. Add a different User");
        }

        boolean isConnected = connectionService.checkIfUserAlreadyExistInConnection(owner, connection);
        if (isConnected) throw new ConnectionException(connection.getFullName() + " user is already in my connection");

        connectionService.addConnection(owner, connection);
        MessageResponse  messageResponse = new MessageResponse("Connection Added Successfully!!");
        return new ResponseEntity<>(messageResponse, new HttpHeaders(), HttpStatus.CREATED);

    }
}
