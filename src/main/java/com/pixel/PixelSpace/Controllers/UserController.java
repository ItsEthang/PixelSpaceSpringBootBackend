package com.pixel.PixelSpace.Controllers;

import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Exceptions.InvalidOperationException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.RequestBodies.UserCommentRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserFollowRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserPatchRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserPostRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserRequest;
import com.pixel.PixelSpace.Services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User registration
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

    // User authentication
    @PostMapping("login")
    public ResponseEntity<Void> userLogin(@RequestBody User user) throws AuthenticationException {
        userService.userLogin(user.getUsername(), user.getPassword());
        return ResponseEntity.noContent().header("username", user.getUsername()).build();
    }

    // Get User Profile Management
    @GetMapping("all")
    public ResponseEntity<List<User>> userGetAll() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @GetMapping("")
    public ResponseEntity<User> userGetById(@RequestBody UserRequest request) {
        return ResponseEntity.status(200).body(userService.getUserById(request.getUserId()));
    }

    @PatchMapping("")
    public ResponseEntity<String> userPatch(@RequestBody UserPatchRequest request) {
        userService.userUpdate(request.getUserId(), request.getName(), request.getBio(), request.getEmail(),
                request.getProfileImg());
        return ResponseEntity.ok().body("User updated");
    }

    @DeleteMapping("")
    public ResponseEntity<String> userDeleteById(@RequestBody UserRequest request) {
        Integer userId = request.getUserId();
        userService.userDelete(userId);
        return ResponseEntity.ok().body("User " + userId + " deleted");
    }

    // ---Friendship Actions---
    // Get followers
    @GetMapping("follower")
    public ResponseEntity<List<User>> userGetFollowers(@RequestBody UserRequest request) {
        User user = userService.getUserById(request.getUserId());
        List<Friendship> receivedFriends = user.getReceivedFriendships();
        List<User> allFollowers = new LinkedList<>();
        receivedFriends.forEach((friendship) -> {
            allFollowers.add(friendship.getUser1());
        });
        return ResponseEntity.status(200).body(allFollowers);
    }

    // Get following
    @GetMapping("following")
    public ResponseEntity<List<User>> userGetFollowing(@RequestBody UserRequest request) {
        User user = userService.getUserById(request.getUserId());
        List<Friendship> initiatedFriends = user.getInitiatedFriendships();
        List<User> allFollowings = new LinkedList<>();
        initiatedFriends.forEach((friendship) -> {
            allFollowings.add(friendship.getUser2());
        });
        return ResponseEntity.status(200).body(allFollowings);
    }

    @PostMapping("follow")
    public ResponseEntity<String> userGetFriend(@RequestBody UserFollowRequest request)
            throws InvalidOperationException {
        userService.createFriendship(request.getUserId(), request.getUserId2());
        return ResponseEntity.ok().body("User made friend with user " + request.getUserId2() + "! How lucky :)");
    }

    @DeleteMapping("follow")
    public ResponseEntity<String> userDeleteFriend(@RequestBody UserFollowRequest request) {
        userService.deleteFriendship(request.getUserId(), request.getUserId2());
        return ResponseEntity.ok().body("User unfriended with user " + request.getUserId2() + "! How sad :(");
    }

    // ---Post Actions---
    @PostMapping("post")
    public ResponseEntity<String> userMakePost(@RequestBody UserPostRequest request) {
        Integer userId = request.getUserId();
        Post post = request.getPost();
        userService.userMakePost(userId, post);
        return ResponseEntity.ok().body("User " + userId + " made a post titled: " + post.getTitle());
    }

    @GetMapping("post")
    public ResponseEntity<List<Post>> userGetPost(@RequestBody UserRequest request) {
        return ResponseEntity.ok().body(userService.getUserPosts(request.getUserId()));
    }

    @PostMapping("post/like")
    public ResponseEntity<String> userLikePost(@RequestBody UserPostRequest request) {
        userService.likePost(request.getUserId(), request.getPostId());
        return ResponseEntity.ok().body("User " + request.getUserId() + " liked Post " + request.getPostId());
    }

    @DeleteMapping("post/like")
    public ResponseEntity<String> userUnlikePost(@RequestBody UserPostRequest request) {
        userService.unlikePost(request.getUserId(), request.getPostId());
        return ResponseEntity.ok().body("User " + request.getUserId() + " unliked Post " + request.getPostId());
    }

    // ---Comment Actions---

    @PostMapping("post/comment")
    public ResponseEntity<String> userMakeComment(@RequestBody UserCommentRequest request) {
        userService.userMakeComment(request.getUserId(), request.getPostId(), request.getComment());
        return ResponseEntity.ok().body("User " + request.getUserId() + " made a comment.");
    }

    @PostMapping("post/comment/like")
    public ResponseEntity<String> userLikeComment(@RequestBody UserCommentRequest request) {
        userService.likePostComment(request.getUserId(), request.getPostId(), request.getCommentId());
        return ResponseEntity.ok().body("User " + request.getUserId() + " liked comment " + request.getCommentId());
    }

    @DeleteMapping("post/comment/like")
    public ResponseEntity<String> userUnlikeComment(@RequestBody UserCommentRequest request) {
        userService.unlikePostComment(request.getUserId(), request.getPostId(), request.getCommentId());
        return ResponseEntity.ok().body("User " + request.getUserId() + " unliked comment " + request.getCommentId());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }

}
