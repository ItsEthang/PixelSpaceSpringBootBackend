package com.pixel.PixelSpace.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.PixelSpace.Models.Friendship;
import com.pixel.PixelSpace.Models.User;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    Optional<Friendship> findByUser1AndUser2(User user1, User user2);

    List<Friendship> findAllByUser1OrUser2(User user1, User user2);

    List<Friendship> findAllByUser1(User user1);

    List<Friendship> findAllByUser2(User user2);

}
