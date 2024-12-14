package com.pixel.PixelSpace.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Repositories.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Integer commentId) {
        if (commentRepository.findById(commentId).isPresent()) {
            commentRepository.deleteById(commentId);
        }
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

}
