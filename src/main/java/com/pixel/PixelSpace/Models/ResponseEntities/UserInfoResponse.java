package com.pixel.PixelSpace.Models.ResponseEntities;

public class UserInfoResponse extends UserResponse {
    String bio;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String username, String name, String profileImg, String bio) {
        super(username, name, profileImg);
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
