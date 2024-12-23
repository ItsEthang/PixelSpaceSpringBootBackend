package com.pixel.PixelSpace;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixel.PixelSpace.Controllers.PostController;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Services.PostService;

@WebMvcTest(PostController.class)
class PostControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost_Success() throws Exception {
        Post post = new Post();
        post.setPostId(0);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New post is made!"));

        verify(postService, times(1)).postCreate(post);
    }

    @Test
    void testCreatePost_Failure_EmptyFields() throws Exception {
        Post post = new Post();
        post.setPostId(0);
        post.setTitle("");
        post.setContent("");

        mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Post title and content cannot be empty."));

        verify(postService, never()).postCreate(any(Post.class));
    }

    @Test
    void testGetAllPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(posts.size()));

        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void testGetPostById() throws Exception {
        Post post = new Post();
        post.setPostId(1);
        post.setTitle("Test Post");
        post.setContent("Test Content");

        when(postService.getPostById(1)).thenReturn(post);

        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.content").value("Test Content"));

        verify(postService, times(1)).getPostById(1);
    }

    @Test
    void testGetPostUser() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setName("Test User");
        user.setProfileImg("profile.jpg");

        when(postService.getPostUser(1)).thenReturn(user);

        mockMvc.perform(get("/post/1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.profileImg").value("profile.jpg"));

        verify(postService, times(1)).getPostUser(1);
    }

    @Test
    void testGetPostComments() throws Exception {
        List<Comment> comments = new ArrayList<>();
        when(postService.getPostComment(1)).thenReturn(comments);

        mockMvc.perform(get("/post/1/comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(comments.size()));

        verify(postService, times(1)).getPostComment(1);
    }

    @Test
    void testGetPostLikeCount() throws Exception {
        when(postService.getLikeCount(1)).thenReturn(10);

        mockMvc.perform(get("/post/1/like"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        verify(postService, times(1)).getLikeCount(1);
    }

    @Test
    void testUpdatePost() throws Exception {
        mockMvc.perform(patch("/post/1")
                .param("title", "Updated Title")
                .param("content", "Updated Content"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post updated"));

        verify(postService, times(1)).postUpdate(1, "Updated Title", "Updated Content");
    }

    @Test
    void testDeletePost() throws Exception {
        mockMvc.perform(delete("/post/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Post deleted"));

        verify(postService, times(1)).postDelete(1);
    }
}
