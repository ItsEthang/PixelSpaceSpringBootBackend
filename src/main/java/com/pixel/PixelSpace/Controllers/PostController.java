package com.pixel.PixelSpace.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.ResponseEntities.UserResponse;
import com.pixel.PixelSpace.Services.PostService;

@RestController
@RequestMapping("post")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        if (!post.getTitle().isEmpty() && !post.getContent().isEmpty()) {
            postService.postCreate(post);
            return ResponseEntity.status(HttpStatus.CREATED).body("New post is made!");
        }
        return ResponseEntity.status(400).body("Post title and content cannot be empty.");
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> postGetAll(@RequestParam(required = false) String title,
            @RequestParam(required = false) String username) {
        List<Post> posts = postService.getAllPosts();
        boolean validTitle = title != null && !title.isEmpty();
        boolean validUsername = username != null && !username.isEmpty();
        if (validTitle) {
            posts = postService.getPostsByTitle(title);
        }
        if (validUsername) {
            posts = postService.getPostsByUsername(username);
        }
        if (validTitle && validUsername) {
            posts = postService.getPostsByTitleAndUsername(title, username);
        }
        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Post> postGetById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(postService.getPostById(id));
    }

    @GetMapping("{id}/user")
    public ResponseEntity<UserResponse> postGetUser(@PathVariable Integer id) {
        User user = postService.getPostUser(id);
        UserResponse res = new UserResponse(user.getUserId(), user.getUsername(), user.getName(), user.getProfileImg());
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("{id}/comment")
    public ResponseEntity<List<Comment>> postGetComment(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(postService.getPostComment(id));
    }

    @GetMapping("{id}/like")
    public ResponseEntity<Integer> postGetLikeCount(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(postService.getLikeCount(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> postPatch(@PathVariable Integer id, @RequestParam String title,
            @RequestParam String content) {
        postService.postUpdate(id, title, content);
        return ResponseEntity.ok().body("Post updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> postDelete(@PathVariable Integer id) {
        postService.postDelete(id);
        return ResponseEntity.ok().body("Post deleted");
    }

}
