package com.pixel.PixelSpace.Models.RequestBodies;

public class UserPatchRequest {
    String name;
    String bio;
    String email;
    String profileImg;

    public UserPatchRequest() {
    }

    public UserPatchRequest(String name, String bio, String email, String profileImg) {
        this.name = name;
        this.bio = bio;
        this.email = email;
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
