package com.pixel.PixelSpace.Models.ResponseEntities;

public class UserInfoResponse extends UserResponse {
    String username;
    String bio;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String name, String profileImg, String username, String bio) {
        super(name, profileImg);
        this.username = username;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
