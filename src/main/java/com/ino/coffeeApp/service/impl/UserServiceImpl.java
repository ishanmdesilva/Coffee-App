package com.ino.coffeeApp.service.impl;

import com.ino.coffeeApp.entity.User;
import com.ino.coffeeApp.exception.ValidateRecordException;
import com.ino.coffeeApp.repository.UserRepository;
import com.ino.coffeeApp.service.UserService;
import com.ino.coffeeApp.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if(optUser.isEmpty()) {
            throw new ValidateRecordException("User not found.", ErrorCodes.ENTITY_NOT_FOUND);
        }
        return optUser.get();
    }

    @Override
    public User getById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isEmpty()) {
            throw new ValidateRecordException("User not found.", ErrorCodes.ENTITY_NOT_FOUND);
        }
        return optUser.get();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
