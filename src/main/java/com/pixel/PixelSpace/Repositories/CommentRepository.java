package com.pixel.PixelSpace.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pixel.PixelSpace.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
