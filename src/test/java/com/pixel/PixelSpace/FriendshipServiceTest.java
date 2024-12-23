package com.pixel.PixelSpace;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.FriendshipRepository;
import com.pixel.PixelSpace.Services.FriendshipService;

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

class FriendshipServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFriendship() {
        Friendship friendship = new Friendship();
        friendshipService.createFriendship(friendship);

        verify(friendshipRepository, times(1)).save(friendship);
    }

    @Test
    void testDeleteFriendship() {
        Friendship friendship = new Friendship();
        friendshipService.deleteFriendship(friendship);

        verify(friendshipRepository, times(1)).delete(friendship);
    }

    @Test
    void testFindFriendshipByBothUser_Found() {
        User user1 = new User();
        User user2 = new User();
        Friendship friendship = new Friendship();
        when(friendshipRepository.findByUser1AndUser2(user1, user2)).thenReturn(Optional.of(friendship));

        Optional<Friendship> result = friendshipService.findFriendshipByBothUser(user1, user2);

        assertTrue(result.isPresent());
        assertEquals(friendship, result.get());
        verify(friendshipRepository, times(1)).findByUser1AndUser2(user1, user2);
    }

    @Test
    void testFindFriendshipByBothUser_NotFound() {
        User user1 = new User();
        User user2 = new User();
        when(friendshipRepository.findByUser1AndUser2(user1, user2)).thenReturn(Optional.empty());

        Optional<Friendship> result = friendshipService.findFriendshipByBothUser(user1, user2);

        assertFalse(result.isPresent());
        verify(friendshipRepository, times(1)).findByUser1AndUser2(user1, user2);
    }

    @Test
    void testGetAllFriendship() {
        List<Friendship> friendships = Arrays.asList(new Friendship(), new Friendship());
        when(friendshipRepository.findAll()).thenReturn(friendships);

        List<Friendship> result = friendshipService.getAllFriendship();

        assertEquals(2, result.size());
        verify(friendshipRepository, times(1)).findAll();
    }

    @Test
    void testGetFriendshipById_Found() {
        Friendship friendship = new Friendship();
        when(friendshipRepository.findById(1)).thenReturn(Optional.of(friendship));

        Friendship result = friendshipService.getFriendshipById(1);

        assertEquals(friendship, result);
        verify(friendshipRepository, times(1)).findById(1);
    }

    @Test
    void testGetFriendshipById_NotFound() {
        when(friendshipRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> friendshipService.getFriendshipById(1));
    }

    @Test
    void testGetFriendshipOfUser1() {
        User user1 = new User();
        List<Friendship> friendships = Arrays.asList(new Friendship(), new Friendship());
        when(friendshipRepository.findAllByUser1(user1)).thenReturn(friendships);

        List<Friendship> result = friendshipService.getFriendshipOfUser1(user1);

        assertEquals(2, result.size());
        verify(friendshipRepository, times(1)).findAllByUser1(user1);
    }
}
