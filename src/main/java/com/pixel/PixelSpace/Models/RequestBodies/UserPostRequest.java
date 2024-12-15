package com.pixel.PixelSpace.Models.RequestBodies;

import com.pixel.PixelSpace.Models.Post;

public class UserPostRequest extends UserRequest {
    Integer postId;
    Post post;

    public UserPostRequest() {
    }

    public UserPostRequest(Integer userId, Post post) {
        super(userId);
        this.post = post;
    }

    public UserPostRequest(Integer userId, Integer postId) {
        super(userId);
        this.postId = postId;
    }

    public UserPostRequest(Integer userId, Integer postId, Post post) {
        super(userId);
        this.postId = postId;
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
