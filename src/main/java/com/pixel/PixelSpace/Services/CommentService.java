package com.pixel.PixelSpace.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixel.PixelSpace.Exceptions.ResourceNotFoundException;
import com.pixel.PixelSpace.Models.Comment;
import com.pixel.PixelSpace.Models.Post;
import com.pixel.PixelSpace.Models.User;
import com.pixel.PixelSpace.Repositories.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public User getCommentUser(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment" + commentId + " not found"));
        return comment.getUser();
    }

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Integer commentId) {
        if (commentRepository.findById(commentId).isPresent()) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ResourceNotFoundException("Comment " + commentId + " not found");
        }
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllCommentByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

}
