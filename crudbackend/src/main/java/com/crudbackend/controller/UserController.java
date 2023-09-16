package com.crudbackend.controller;

import com.crudbackend.model.User;
import com.crudbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // create user
    @PostMapping("/users")
    public User createUser(@RequestBody(required = true)User user){
        return userRepository.save(user);
    }
}
