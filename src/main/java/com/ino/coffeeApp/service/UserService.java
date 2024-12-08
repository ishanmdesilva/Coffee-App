package com.ino.coffeeApp.service;

import com.ino.coffeeApp.entity.User;

import java.util.List;

public interface UserService {

    User getByUsername(String username);

    User getById(Long id);

    User addUser(User user);

    List<User> getAll();

}
