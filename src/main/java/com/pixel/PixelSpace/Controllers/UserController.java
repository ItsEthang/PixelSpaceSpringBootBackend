package com.pixel.PixelSpace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<String> userRegister(@RequestBody User user) {
        List<User> allUsers = userService.getAllUsers();
        for (User a : allUsers) {
            if (a.getUsername().equals(user.getUsername())) {
                return ResponseEntity.status(409).body("User already exists");
            }
        }
        if (!user.getUsername().isEmpty() && user.getPassword().length() > 3) {
            userService.userRegister(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("New user registered");
        }
        return ResponseEntity.status(400).body("An unknown error occurred during registering");
    }

    @GetMapping("")
    public ResponseEntity<List<User>> userGetAll() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> userGetById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(userService.getUserById(id));
    }
}
