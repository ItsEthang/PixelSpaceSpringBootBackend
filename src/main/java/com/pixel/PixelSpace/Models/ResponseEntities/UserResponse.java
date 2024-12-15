package com.pixel.PixelSpace.Models.ResponseEntities;

public class UserResponse {
    String name;
    String profileImg;

    public UserResponse() {
    }

    public UserResponse(String name, String profileImg) {
        this.name = name;
        this.profileImg = profileImg;
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

}
