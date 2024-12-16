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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import com.pixel.PixelSpace.Exceptions.InvalidOperationException;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.RequestBodies.UserCommentRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserFollowRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserPatchRequest;
import com.pixel.PixelSpace.Models.RequestBodies.UserPostRequest;
import com.pixel.PixelSpace.Models.ResponseEntities.UserInfoResponse;
import com.pixel.PixelSpace.Models.ResponseEntities.UserResponse;
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
        User loggedUser = userService.userLogin(user.getUsername(), user.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", Integer.toString(loggedUser.getUserId()));
        return ResponseEntity.ok().headers(headers).build();
    }

    // Get User Profile Management
    @GetMapping("all")
    public ResponseEntity<List<UserInfoResponse>> userGetAll() {
        List<User> users = userService.getAllUsers();
        List<UserInfoResponse> usersInfo = new LinkedList<>();
        for (User user : users) {
            UserInfoResponse userInfo = new UserInfoResponse(user.getUsername(), user.getName(), user.getProfileImg(),
                    user.getBio());
            usersInfo.add(userInfo);
        }
        return ResponseEntity.status(200).body(usersInfo);
    }

    @GetMapping("")
    public ResponseEntity<UserInfoResponse> userGetById(@RequestHeader String userId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        UserInfoResponse userInfo = new UserInfoResponse(user.getUsername(), user.getName(), user.getProfileImg(),
                user.getBio());
        return ResponseEntity.status(200).body(userInfo);
    }

    @GetMapping("info")
    public ResponseEntity<UserInfoResponse> userGetByUsername(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        UserInfoResponse userInfo = new UserInfoResponse(user.getUsername(), user.getName(), user.getProfileImg(),
                user.getBio());
        return ResponseEntity.status(200).body(userInfo);
    }

    @PatchMapping("")
    public ResponseEntity<String> userPatch(@RequestHeader String userId, @RequestBody UserPatchRequest request) {
        userService.userUpdate(Integer.valueOf(userId), request.getName(), request.getBio(), request.getEmail(),
                request.getProfileImg());
        return ResponseEntity.ok().body("User updated");
    }

    @DeleteMapping("")
    public ResponseEntity<String> userDeleteById(@RequestHeader String userId) {
        Integer user1Id = Integer.valueOf(userId);
        userService.userDelete(user1Id);
        return ResponseEntity.ok().body("User " + userId + " deleted");
    }

    // ---Friendship Actions---
    // Get followers
    @GetMapping("follower")
    public ResponseEntity<List<UserResponse>> userGetFollowers(@RequestHeader String userId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        List<Friendship> receivedFriends = user.getReceivedFriendships();
        List<UserResponse> allFollowers = new LinkedList<>();
        receivedFriends.forEach((friendship) -> {
            User follower = friendship.getUser1();
            UserResponse res = new UserResponse(follower.getUsername(), follower.getName(), follower.getProfileImg());
            allFollowers.add(res);
        });
        return ResponseEntity.status(200).body(allFollowers);
    }

    // Get following
    @GetMapping("following")
    public ResponseEntity<List<UserResponse>> userGetFollowing(@RequestHeader String userId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        List<Friendship> initiatedFriends = user.getInitiatedFriendships();
        List<UserResponse> allFollowings = new LinkedList<>();
        initiatedFriends.forEach((friendship) -> {
            User following = friendship.getUser2();
            UserResponse res = new UserResponse(following.getUsername(), following.getName(),
                    following.getProfileImg());
            allFollowings.add(res);
        });
        return ResponseEntity.status(200).body(allFollowings);
    }

    @PostMapping("follow")
    public ResponseEntity<String> userGetFriend(@RequestHeader String userId, @RequestBody UserFollowRequest request)
            throws InvalidOperationException {
        userService.createFriendship(Integer.valueOf(userId), request.getUserId2());
        return ResponseEntity.ok().body("User made friend with user " + request.getUserId2() + "! How lucky :)");
    }

    @DeleteMapping("follow")
    public ResponseEntity<String> userDeleteFriend(@RequestHeader String userId,
            @RequestBody UserFollowRequest request) {
        userService.deleteFriendship(Integer.valueOf(userId), request.getUserId2());
        return ResponseEntity.ok().body("User unfriended with user " + request.getUserId2() + "! How sad :(");
    }

    // ---Post Actions---
    @PostMapping("post")
    public ResponseEntity<String> userMakePost(@RequestHeader String userId, @RequestBody UserPostRequest request) {
        Integer user1Id = Integer.valueOf(userId);
        Post post = request.getPost();
        userService.userMakePost(user1Id, post);
        return ResponseEntity.ok().body("User " + userId + " made a post titled: " + post.getTitle());
    }

    @GetMapping("post")
    public ResponseEntity<List<Post>> userGetPost(@RequestHeader String userId) {
        return ResponseEntity.ok().body(userService.getUserPosts(Integer.valueOf(userId)));
    }

    @PostMapping("post/like")
    public ResponseEntity<String> userLikePost(@RequestHeader String userId, @RequestBody UserPostRequest request) {
        userService.likePost(Integer.valueOf(userId), request.getPostId());
        return ResponseEntity.ok().body("User " + Integer.valueOf(userId) + " liked Post " + request.getPostId());
    }

    @DeleteMapping("post/like")
    public ResponseEntity<String> userUnlikePost(@RequestHeader String userId, @RequestBody UserPostRequest request) {
        userService.unlikePost(Integer.valueOf(userId), request.getPostId());
        return ResponseEntity.ok().body("User " + userId + " unliked Post " + request.getPostId());
    }

    // ---Comment Actions---

    @PostMapping("post/comment")
    public ResponseEntity<String> userMakeComment(@RequestHeader String userId,
            @RequestBody UserCommentRequest request) {
        userService.userMakeComment(Integer.valueOf(userId), request.getPostId(), request.getComment());
        return ResponseEntity.ok().body("User " + userId + " made a comment.");
    }

    @PostMapping("post/comment/like")
    public ResponseEntity<String> userLikeComment(@RequestHeader String userId,
            @RequestBody UserCommentRequest request) {
        userService.likePostComment(Integer.valueOf(userId), request.getPostId(), request.getCommentId());
        return ResponseEntity.ok().body("User " + userId + " liked comment " + request.getCommentId());
    }

    @DeleteMapping("post/comment/like")
    public ResponseEntity<String> userUnlikeComment(@RequestHeader String userId,
            @RequestBody UserCommentRequest request) {
        userService.unlikePostComment(Integer.valueOf(userId), request.getPostId(), request.getCommentId());
        return ResponseEntity.ok().body("User " + userId + " unliked comment " + request.getCommentId());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex) {
        return ex.getMessage();
    }

}
