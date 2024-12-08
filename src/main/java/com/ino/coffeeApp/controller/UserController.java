package com.ino.coffeeApp.controller;

import com.ino.coffeeApp.entity.MenuItem;
import com.ino.coffeeApp.entity.User;
import com.ino.coffeeApp.service.MenuItemService;
import com.ino.coffeeApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/create")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User saveUser = userService.addUser(user);

        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User saveUser = userService.getByUsername(username);

        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User saveUser = userService.getById(id);

        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {
        MenuItem saveUser = menuItemService.getById(id);

        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

}
