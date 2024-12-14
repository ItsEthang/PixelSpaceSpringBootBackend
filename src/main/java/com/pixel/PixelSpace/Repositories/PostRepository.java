package com.pixel.PixelSpace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);
}
