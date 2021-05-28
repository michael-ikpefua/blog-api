package com.michael.service;

import com.michael.model.Connection;
import com.michael.model.User;
import com.michael.repository.ConnectionRepository;
import com.michael.repository.UserRepository;
import com.michael.service.contracts.IConnectionService;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ConnectionService implements IConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void addConnection(User owner, User connection) {
        Connection newConnection = new Connection();
        newConnection.setOwner(owner);
        newConnection.setConnecton(connection);

        connectionRepository.save(newConnection);
    }

    @Override
    public boolean checkIfUserAlreadyExistInConnection(User owner, User connection) {
        Optional<Connection> optionalConnection = connectionRepository.findConnectionByOwnerAndConnecton(owner, connection);

        return optionalConnection.isPresent();
    }

    @Override
    public List<User> getConnections(User owner) {
        List<Connection> connections = connectionRepository.findConnectionByOwner(owner);
        if (connections != null) {
            List<Long> connectionId = connections.stream()
                    .map(connection -> connection.getConnecton().getId())
                    .toList();
            return  userRepository.findByIdIn(connectionId);
        }

        return null;
    }
}
