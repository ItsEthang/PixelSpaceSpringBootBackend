package com.pixel.PixelSpace.Services;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

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

    public void userDelete(Integer id) {
        Optional<User> validUser = userRepository.findById(id);
        if (validUser.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        Optional<User> validUser = userRepository.findById(userId);
        return validUser
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + userId + " is not found"));
    }

    public User userLogin(String username, String password) throws AuthenticationException {
        Optional<User> validLogin = userRepository.findByUsernameAndPassword(username, password);
        return validLogin.orElseThrow(() -> new AuthenticationException("The username or the password is incorrect"));
    }

    public void userUpdate(Integer id, String name, String bio, String email, String profileImg) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user of id " + id + " is not found"));
        userToUpdate.setName(name);
        userToUpdate.setBio(bio);
        userToUpdate.setEmail(email);
        userToUpdate.setProfileImg(profileImg);
        userRepository.save(userToUpdate);
    }

}
