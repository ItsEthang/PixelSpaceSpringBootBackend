package com.pixel.PixelSpace;

import javax.naming.AuthenticationException;
import com.pixel.PixelSpace.Exceptions.InvalidOperationException;
import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.*;
import com.pixel.PixelSpace.Repositories.UserRepository;
import com.pixel.PixelSpace.Services.CommentService;
import com.pixel.PixelSpace.Services.FriendshipService;
import com.pixel.PixelSpace.Services.LikeService;
import com.pixel.PixelSpace.Services.PostService;
import com.pixel.PixelSpace.Services.UserService;

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

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostService postService;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private CommentService commentService;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegister() {
        User user = new User();
        userService.userRegister(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUserDelete_ExistingUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        userService.userDelete(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testUserDelete_NonExistingUser() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        userService.userDelete(1);

        verify(userRepository, never()).deleteById(1);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_ExistingUser() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testGetUserById_NonExistingUser() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void testUserLogin_Success() throws AuthenticationException {
        User user = new User();
        when(userRepository.findByUsernameAndPassword("test", "password")).thenReturn(Optional.of(user));

        User result = userService.userLogin("test", "password");

        assertEquals(user, result);
    }

    @Test
    void testUserLogin_Failure() {
        when(userRepository.findByUsernameAndPassword("test", "wrongpassword")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> userService.userLogin("test", "wrongpassword"));
    }

    @Test
    void testUserUpdate() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.userUpdate(1, "newName", "newBio", "newEmail", "newProfileImg");

        assertEquals("newName", user.getName());
        assertEquals("newBio", user.getBio());
        assertEquals("newEmail", user.getEmail());
        assertEquals("newProfileImg", user.getProfileImg());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLikePost_Success() {
        User user = new User();
        Post post = new Post();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postService.getPostById(1)).thenReturn(post);
        when(likeService.findLikeByUserAndPost(user, post)).thenReturn(Optional.empty());

        userService.likePost(1, 1);

        verify(likeService, times(1)).createLike(any(Like.class));
    }

    @Test
    void testLikePost_AlreadyLiked() {
        User user = new User();
        Post post = new Post();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postService.getPostById(1)).thenReturn(post);
        when(likeService.findLikeByUserAndPost(user, post)).thenReturn(Optional.of(new Like()));

        assertThrows(InvalidOperationException.class, () -> userService.likePost(1, 1));
    }

    @Test
    void testCreateFriendship_Success() throws InvalidOperationException {
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));
        when(friendshipService.findFriendshipByBothUser(user1, user2)).thenReturn(Optional.empty());

        userService.createFriendship(1, 2);

        verify(friendshipService, times(1)).createFriendship(any(Friendship.class));
    }

    @Test
    void testCreateFriendship_AlreadyFriends() {
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));
        when(friendshipService.findFriendshipByBothUser(user1, user2)).thenReturn(Optional.of(new Friendship()));

        assertThrows(InvalidOperationException.class, () -> userService.createFriendship(1, 2));
    }
}
