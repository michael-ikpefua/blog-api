package com.michael.service;

import com.michael.model.Connection;
import com.michael.model.User;
import com.michael.repository.ConnectionRepository;
import com.michael.repository.UserRepository;
import com.michael.service.contracts.IConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ConnectionService implements IConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

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
}
