package com.pixel.PixelSpace.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.FriendshipRepository;

@Service
public class FriendshipService {

    private FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void createFriendship(Friendship friendship) {
        friendshipRepository.save(friendship);
    }

    public void deleteFriendship(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }

    public Optional<Friendship> findFriendshipByBothUser(User user1, User user2) throws ResourceNotFoundException {
        return friendshipRepository.findByUser1AndUser2(user1, user2);
    }

    public List<Friendship> getAllFriendship() {
        return friendshipRepository.findAll();
    }

    public Friendship getFriendshipById(Integer id) {

        return friendshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The friendship of id " + id + " is not found"));
    }

    public List<Friendship> getFriendshipOfUser1(User user1) {
        return friendshipRepository.findAllByUser1(user1);
    }
}
