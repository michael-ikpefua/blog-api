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
    public User register(UserRegisterRequest userRegisterRequest) {
        User user= new User();
        user.setFullName(userRegisterRequest.getFullName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(userRegisterRequest.getPassword());

        return userRepository.save(user);

    }
}
