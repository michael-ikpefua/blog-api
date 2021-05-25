package com.michael.service;

import com.michael.model.User;
import com.michael.repository.UserRepository;
import com.michael.request.UserLoginRequest;
import com.michael.request.UserRegisterRequest;
import com.michael.service.contracts.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public User login(UserLoginRequest request) {
        User user = null;
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            return user;
        }

        return user;
    }

    public boolean checkIfUserExist(String email) {
       return userRepository.existsUsersByEmail(email);
    }
}
