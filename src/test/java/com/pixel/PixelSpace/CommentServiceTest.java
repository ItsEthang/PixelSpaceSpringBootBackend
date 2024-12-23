package com.pixel.PixelSpace;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.CommentRepository;
import com.pixel.PixelSpace.Services.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentUser_Found() {
        User user = new User();
        Comment comment = new Comment();
        comment.setUser(user);
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        User result = commentService.getCommentUser(1);

        assertEquals(user, result);
        verify(commentRepository, times(1)).findById(1);
    }

    @Test
    void testGetCommentUser_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.getCommentUser(1));
    }

    @Test
    void testCreateComment() {
        Comment comment = new Comment();
        commentService.createComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testDeleteComment_Found() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(new Comment()));

        commentService.deleteComment(1);

        verify(commentRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteComment_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(1));
    }

    @Test
    void testGetAllComment() {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> result = commentService.getAllComment();

        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCommentByPost() {
        Post post = new Post();
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(commentRepository.findAllByPost(post)).thenReturn(comments);

        List<Comment> result = commentService.getAllCommentByPost(post);

        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findAllByPost(post);
    }

    @Test
    void testGetCommentById_Found() {
        Comment comment = new Comment();
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        Comment result = commentService.getCommentById(1);

        assertEquals(comment, result);
        verify(commentRepository, times(1)).findById(1);
    }

    @Test
    void testGetCommentById_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.getCommentById(1));
    }

    @Test
    void testGetLikeCount() {
        Comment comment = new Comment();
        comment.setLikes(Arrays.asList(new Like(), new Like()));
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        int result = commentService.getLikeCount(1);

        assertEquals(2, result);
        verify(commentRepository, times(1)).findById(1);
    }
}
