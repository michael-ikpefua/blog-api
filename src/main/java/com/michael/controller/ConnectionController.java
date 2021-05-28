package com.michael.controller;

import com.michael.exceptions.ConnectionException;
import com.michael.exceptions.UserException;
import com.michael.model.Connection;
import com.michael.model.User;
import com.michael.repository.UserRepository;
import com.michael.response.ConnectionResponse;
import com.michael.response.MessageResponse;
import com.michael.service.UserService;
import com.michael.service.contracts.IConnectionService;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "connections")
public class ConnectionController extends BaseController{

    @Autowired
    IConnectionService connectionService;

    @Autowired
    UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<?> index(HttpSession session) {
        User owner = authUser(session);

        if (owner == null ) throw new UserException("User Not Found. Please Login!!!");

        List<User> connections = connectionService.getConnections(owner);
        ConnectionResponse connectionResponse = new ConnectionResponse();
        connectionResponse.setMessage("List of " + owner.getFullName() + " connections");
        connectionResponse.setConnections(connections);

        return new ResponseEntity<>(connectionResponse, new HttpHeaders(), HttpStatus.OK);

    }

    @PostMapping(path = "{connectionId}/add-connection")
    public ResponseEntity<?> store(@PathVariable Long connectionId, HttpSession session) {
        User owner = authUser(session);
        if (owner == null) throw new UserException("Please login, before you can add user to connection");

        User connection = userService.getUserById(connectionId);
        if (connection == null) throw new UserException("User account Not Found!!!");

        if (owner.getId().equals(connection.getId()))
            throw new UserException("I cannot add myself to my connection. Add a different User");

        boolean isConnected = connectionService.checkIfUserAlreadyExistInConnection(owner, connection);
        if (isConnected) throw new ConnectionException(connection.getFullName() + " user is already in my connection");

        connectionService.addConnection(owner, connection);
        MessageResponse  messageResponse = new MessageResponse("Connection Added Successfully!!");
        return new ResponseEntity<>(messageResponse, new HttpHeaders(), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "{connectionId}")
    public ResponseEntity<?> destroy(@PathVariable Long connectionId, HttpSession session) {
        User owner = authUser(session);
        if (owner == null) throw new UserException("User Not Found. Please Login");
        User connection = userService.getUserById(connectionId);
        if (connection == null) throw new UserException("User Not Found. Check User Id");

        boolean isConnected = connectionService.checkIfUserAlreadyExistInConnection(owner, connection);
        if (!isConnected) {
            throw new ConnectionException("Connection Not In List");
        }

        connectionService.removeConnection(owner, connection);

        MessageResponse messageResponse = new MessageResponse("Connection Removed!!!");

        return new ResponseEntity<>(messageResponse, new HttpHeaders(), HttpStatus.OK);
    }

}
