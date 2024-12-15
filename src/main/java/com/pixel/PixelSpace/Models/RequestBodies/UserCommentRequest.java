package com.pixel.PixelSpace.Models.RequestBodies;

import com.pixel.PixelSpace.Models.Comment;

public class UserCommentRequest extends UserPostRequest {
    Integer commentId;
    Comment comment;

    public UserCommentRequest() {

    }

    public UserCommentRequest(Integer commentId, Comment comment) {
        this.commentId = commentId;
        this.comment = comment;
    }

    public UserCommentRequest(Integer userId, Integer postId, Integer commentId) {
        super(userId, postId);
        this.commentId = commentId;
    }

    public UserCommentRequest(Integer userId, Integer postId, Comment comment) {
        super(userId, postId);
        this.comment = comment;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
