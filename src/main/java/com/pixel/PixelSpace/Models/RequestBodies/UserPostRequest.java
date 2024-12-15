package com.pixel.PixelSpace.Models.RequestBodies;

import com.pixel.PixelSpace.Models.Post;

public class UserPostRequest extends UserRequest {
    Post post;

    public UserPostRequest() {
    }

    public UserPostRequest(Post post) {
        this.post = post;
    }

    public UserPostRequest(Integer userId, Post post) {
        super(userId);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
