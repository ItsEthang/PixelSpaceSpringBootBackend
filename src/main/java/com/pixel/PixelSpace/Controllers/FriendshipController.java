package com.pixel.PixelSpace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.RequestBodies.UserFollowRequest;
import com.pixel.PixelSpace.Services.FriendshipService;
import com.pixel.PixelSpace.Services.UserService;

@RestController
@RequestMapping("friendship")
public class FriendshipController {
    private FriendshipService friendshipService;
    private UserService userService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<Friendship>> friendshipGetAll() {
        return ResponseEntity.status(200).body(friendshipService.getAllFriendship());
    }

    @GetMapping("following")
    public ResponseEntity<Boolean> isUser1FollowingUser2(@RequestHeader String userId,
            @RequestBody UserFollowRequest request) {
        User user1 = userService.getUserById(Integer.parseInt(userId));
        User user2 = userService.getUserById(request.getUserId2());
        List<Friendship> user1Followings = user1.getInitiatedFriendships();
        for (Friendship following : user1Followings) {
            if (following.getUser2().equals(user2)) {
                ResponseEntity.status(200).body(true);
            }
        }
        return ResponseEntity.status(200).body(false);
    }

}
