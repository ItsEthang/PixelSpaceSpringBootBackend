package com.pixel.PixelSpace.Models.RequestBodies;

public class UserRequest {
    Integer userId;

    public UserRequest() {
    }

    public UserRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
