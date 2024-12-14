package com.pixel.PixelSpace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPost(Post post);
}
