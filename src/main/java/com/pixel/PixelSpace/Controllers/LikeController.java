package com.pixel.PixelSpace.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Services.CommentService;
import com.pixel.PixelSpace.Services.LikeService;
import com.pixel.PixelSpace.Services.PostService;
import com.pixel.PixelSpace.Services.UserService;

@RestController
@RequestMapping("like")
public class LikeController {
    private LikeService likeService;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    @Autowired
    public LikeController(LikeService likeService, UserService userService, PostService postService,
            CommentService commentService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<Boolean> likeGetByUserAndPost(@RequestHeader String userId, @PathVariable Integer postId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        Post post = postService.getPostById(Integer.valueOf(postId));
        Optional<Like> optionalLike = likeService.findLikeByUserAndPost(user, post);
        if (optionalLike.isPresent()) {
            return ResponseEntity.status(200).body(true);
        }
        return ResponseEntity.status(200).body(false);
    }

    @GetMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<Boolean> likeGetByUserAndPostAndComment(@RequestHeader String userId,
            @PathVariable Integer postId, @PathVariable Integer commentId) {
        User user = userService.getUserById(Integer.valueOf(userId));
        Post post = postService.getPostById(Integer.valueOf(postId));
        Comment comment = commentService.getCommentById(Integer.valueOf(commentId));
        Optional<Like> optionalLike = likeService.findLikeByUserAndPostAndComment(user, post, comment);
        if (optionalLike.isPresent()) {
            return ResponseEntity.status(200).body(true);
        }
        return ResponseEntity.status(200).body(false);
    }
}
