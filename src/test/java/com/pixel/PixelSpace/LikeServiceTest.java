package com.pixel.PixelSpace;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.LikeRepository;
import com.pixel.PixelSpace.Services.LikeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLike() {
        Like like = new Like();
        likeService.createLike(like);

        verify(likeRepository, times(1)).save(like);
    }

    @Test
    void testDeleteLike() {
        Like like = new Like();
        likeService.deleteLike(like);

        verify(likeRepository, times(1)).delete(like);
    }

    @Test
    void testFindLikeByUserAndPost() {
        User user = new User();
        Post post = new Post();
        Like like = new Like(post, user);

        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(like));

        Optional<Like> result = likeService.findLikeByUserAndPost(user, post);

        assertTrue(result.isPresent());
        assertEquals(like, result.get());
        verify(likeRepository, times(1)).findByUserAndPost(user, post);
    }

    @Test
    void testFindLikeByUserAndPostAndComment() {
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment();
        Like like = new Like(post, comment, user);

        when(likeRepository.findByUserAndPostAndComment(user, post, comment)).thenReturn(Optional.of(like));

        Optional<Like> result = likeService.findLikeByUserAndPostAndComment(user, post, comment);

        assertTrue(result.isPresent());
        assertEquals(like, result.get());
        verify(likeRepository, times(1)).findByUserAndPostAndComment(user, post, comment);
    }

    @Test
    void testFindLikeByUserAndPost_NotFound() {
        User user = new User();
        Post post = new Post();

        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());

        Optional<Like> result = likeService.findLikeByUserAndPost(user, post);

        assertFalse(result.isPresent());
        verify(likeRepository, times(1)).findByUserAndPost(user, post);
    }
}
