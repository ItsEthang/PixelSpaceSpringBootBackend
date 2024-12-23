package com.pixel.PixelSpace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.PostRepository;
import com.pixel.PixelSpace.Services.CommentService;
import com.pixel.PixelSpace.Services.PostService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<Post> result = postService.getAllPosts();

        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void testPostCreate() {
        Post post = new Post();
        postService.postCreate(post);

        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testGetPostById_Found() {
        Post post = new Post();
        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1);

        assertEquals(post, result);
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1));
    }

    @Test
    void testGetPostUser_Found() {
        User user = new User();
        Post post = new Post();
        post.setUser(user);
        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        User result = postService.getPostUser(1);

        assertEquals(user, result);
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    void testGetPostUser_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPostUser(1));
    }

    @Test
    void testGetPostComment_Found() {
        Post post = new Post();
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(commentService.getAllCommentByPost(post)).thenReturn(comments);

        List<Comment> result = postService.getPostComment(1);

        assertEquals(2, result.size());
        verify(postRepository, times(1)).findById(1);
        verify(commentService, times(1)).getAllCommentByPost(post);
    }

    @Test
    void testPostDelete_Found() {
        when(postRepository.findById(1)).thenReturn(Optional.of(new Post()));

        postService.postDelete(1);

        verify(postRepository, times(1)).deleteById(1);
    }

    @Test
    void testPostDelete_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.postDelete(1));
    }

    @Test
    void testPostUpdate_Found() {
        Post post = new Post();
        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        postService.postUpdate(1, "Updated Title", "Updated Content");

        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getContent());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void testPostUpdate_NotFound() {
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.postUpdate(1, "Title", "Content"));
    }

    @Test
    void testGetLikeCount() {
        Post post = new Post();
        Like like1 = new Like();
        Like like2 = new Like();
        like2.setComment(new Comment()); // This like is tied to a comment.
        post.setLikes(Arrays.asList(like1, like2));
        when(postRepository.findById(1)).thenReturn(Optional.of(post));

        int result = postService.getLikeCount(1);

        assertEquals(1, result);
        verify(postRepository, times(1)).findById(1);
    }
}
