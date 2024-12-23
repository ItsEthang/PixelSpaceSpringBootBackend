package com.pixel.PixelSpace;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pixel.PixelSpace.Controllers.PostController;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Models.ResponseEntities.UserResponse;
import com.pixel.PixelSpace.Services.PostService;

class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost_Success() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        ResponseEntity<String> response = postController.createPost(post);

        verify(postService, times(1)).postCreate(post);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New post is made!", response.getBody());
    }

    @Test
    void testCreatePost_Failure_EmptyFields() {
        Post post = new Post();
        post.setTitle("");
        post.setContent("");

        ResponseEntity<String> response = postController.createPost(post);

        verify(postService, never()).postCreate(any(Post.class));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Post title and content cannot be empty.", response.getBody());
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = new ArrayList<>();
        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<List<Post>> response = postController.postGetAll(null, null);

        verify(postService, times(1)).getAllPosts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
    }

    @Test
    void testGetPostById() {
        Post post = new Post();
        when(postService.getPostById(1)).thenReturn(post);

        ResponseEntity<Post> response = postController.postGetById(1);

        verify(postService, times(1)).getPostById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void testGetPostUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setName("Test User");
        user.setProfileImg("profile.jpg");

        when(postService.getPostUser(1)).thenReturn(user);

        ResponseEntity<UserResponse> response = postController.postGetUser(1);

        verify(postService, times(1)).getPostUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testuser", response.getBody().getUsername());
    }

    @Test
    void testGetPostComments() {
        List<Comment> comments = new ArrayList<>();
        when(postService.getPostComment(1)).thenReturn(comments);

        ResponseEntity<List<Comment>> response = postController.postGetComment(1);

        verify(postService, times(1)).getPostComment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
    }

    @Test
    void testGetPostLikeCount() {
        when(postService.getLikeCount(1)).thenReturn(10);

        ResponseEntity<Integer> response = postController.postGetLikeCount(1);

        verify(postService, times(1)).getLikeCount(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody());
    }

    @Test
    void testUpdatePost() {
        ResponseEntity<String> response = postController.postPatch(1, "Updated Title", "Updated Content");

        verify(postService, times(1)).postUpdate(1, "Updated Title", "Updated Content");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post updated", response.getBody());
    }

    @Test
    void testDeletePost() {
        ResponseEntity<String> response = postController.postDelete(1);

        verify(postService, times(1)).postDelete(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Post deleted", response.getBody());
    }
}
