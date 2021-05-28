package com.michael.service.contracts;

import com.michael.model.User;

import java.util.List;

public interface IConnectionService {

    void addConnection(User owner, User connection);

    boolean checkIfUserAlreadyExistInConnection(User owner, User connection);

    List<User> getConnections(User owner);
}
