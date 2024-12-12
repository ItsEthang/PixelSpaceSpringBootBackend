package com.pixel.PixelSpace.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void userRegister(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        Optional<User> validUser = userRepository.findById(userId);
        return validUser
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + userId + " is not found"));
    }

}
