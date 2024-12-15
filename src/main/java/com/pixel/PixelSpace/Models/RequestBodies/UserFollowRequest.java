package com.pixel.PixelSpace.Models.RequestBodies;

public class UserFollowRequest extends UserRequest {
    Integer userId2;

    public UserFollowRequest() {
    }

    public UserFollowRequest(Integer userId, Integer userId2) {
        super(userId);
        this.userId2 = userId2;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

}
