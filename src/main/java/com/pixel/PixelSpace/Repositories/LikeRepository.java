package com.pixel.PixelSpace.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByUserAndPostAndComment(User user, Post post, Comment comment);

    Optional<Like> findByUserAndPost(User user, Post post);
}
