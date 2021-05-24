package com.michael.service;

import com.michael.model.User;
import com.michael.repository.UserRepository;
import com.michael.request.UserRegisterRequest;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User register(UserRegisterRequest request) {
        User user= new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepository.save(user);

    }
}
