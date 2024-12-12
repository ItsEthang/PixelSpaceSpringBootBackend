package com.pixel.PixelSpace.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "LIKE")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id")
    private int likeId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "user_id")
    private int userId;

    public Like() {

    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + likeId;
        result = prime * result + postId;
        result = prime * result + commentId;
        result = prime * result + userId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Like other = (Like) obj;
        if (likeId != other.likeId)
            return false;
        if (postId != other.postId)
            return false;
        if (commentId != other.commentId)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }

}
