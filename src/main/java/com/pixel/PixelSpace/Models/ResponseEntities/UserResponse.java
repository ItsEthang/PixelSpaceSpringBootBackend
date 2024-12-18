package com.pixel.PixelSpace.Models.ResponseEntities;

public class UserResponse {
    Integer userId;
    String username;
    String name;
    String profileImg;

    public UserResponse() {
    }

    public UserResponse(Integer userId, String username, String name, String profileImg) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.profileImg = profileImg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
