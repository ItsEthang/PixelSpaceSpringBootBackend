package com.pixel.PixelSpace.Models.RequestBodies;

import com.pixel.PixelSpace.Models.Post;

public class UserPostRequest {
    Integer postId;
    Post post;

    public UserPostRequest() {
    }

    public UserPostRequest(Post post) {

        this.post = post;
    }

    public UserPostRequest(Integer postId) {

        this.postId = postId;
    }

    public UserPostRequest(Integer postId, Post post) {

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
