package com.pixel.PixelSpace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Services.FriendshipService;

@RestController
@RequestMapping("friendship")
public class FriendshipController {
    private FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("")
    public ResponseEntity<List<Friendship>> friendshipGetAll() {
        return ResponseEntity.status(200).body(friendshipService.getAllFriendship());
    }

    @GetMapping("{id}")
    public ResponseEntity<Friendship> friendshipGetById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(friendshipService.getFriendshipById(id));
    }
}
