package com.pixel.PixelSpace.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Like;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.LikeRepository;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void createLike(Like like) {
        likeRepository.save(like);
    }

    public void deleteLike(Like like) {
        likeRepository.delete(like);
    }

    public Optional<Like> findLikeByUserAndPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post);
    }

    public Optional<Like> findLikeByUserAndPostAndComment(User user, Post post, Comment comment) {
        return likeRepository.findByUserAndPostAndComment(user, post, comment);
    }
}
