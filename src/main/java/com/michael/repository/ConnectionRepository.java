package com.michael.repository;

import com.michael.model.Connection;
import com.michael.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Optional<Connection> findConnectionByOwnerAndConnecton(User owner, User connection);
}
