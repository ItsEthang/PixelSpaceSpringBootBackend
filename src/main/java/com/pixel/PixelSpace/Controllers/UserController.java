package com.pixel.PixelSpace.Controllers;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Exceptions.InvalidOperationException;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.Post;
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

    @PostMapping("login")
    public ResponseEntity<Void> userLogin(@RequestBody User user) throws AuthenticationException {
        userService.userLogin(user.getUsername(), user.getPassword());
        return ResponseEntity.noContent().header("username", user.getUsername()).build();
    }

    @GetMapping("")
    public ResponseEntity<List<User>> userGetAll() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> userGetById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(userService.getUserById(id));
    }

    @GetMapping("{id}/friend")
    public ResponseEntity<List<Friendship>> userGetFriend(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(userService.getUser1Friendship(id));
    }

    @PostMapping("{id}/friend/{id2}")
    public ResponseEntity<String> userGetFriend(@PathVariable Integer id, @PathVariable Integer id2)
            throws InvalidOperationException {
        userService.createFriendship(id, id2);
        return ResponseEntity.ok().body("User made friend with user " + id2 + "! How lucky :)");
    }

    @DeleteMapping("{id}/friend/{id2}")
    public ResponseEntity<String> userDeleteFriend(@PathVariable Integer id, @PathVariable Integer id2) {
        userService.deleteFriendship(id, id2);
        return ResponseEntity.ok().body("User unfriended with user " + id2 + "! How sad :(");
    }

    @PostMapping("{id}/post")
    public ResponseEntity<String> userMakePost(@PathVariable Integer id, @RequestBody Post post) {
        userService.userMakePost(id, post);
        return ResponseEntity.ok().body("User " + id + " made a post titled " + post.getTitle());
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> userPatch(@PathVariable Integer id, @RequestParam String name,
            @RequestParam String bio, @RequestParam String email, @RequestParam String profileImg) {
        userService.userUpdate(id, name, bio, email, profileImg);
        return ResponseEntity.ok().body("User updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> userDeleteById(@PathVariable Integer id) {
        userService.userDelete(id);
        return ResponseEntity.ok().body("User " + id + " deleted");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }

}
