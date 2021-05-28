package com.michael.service.contracts;

import com.michael.model.User;

public interface IConnectionService {

    void addConnection(User owner, User connection);

    boolean checkIfUserAlreadyExistInConnection(User owner, User connection);
}
