package com.pixel.PixelSpace.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "UserTB")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    private String email;

    @Column(name = "password", length = 50, nullable = false, unique = true)
    private String password;

    private String name;

    @Column(columnDefinition = "TEXT", length = 6000)
    private String bio;

    @Column(name = "profile_img")
    private String profileImg;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference(value = "post")
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference(value = "user-comment")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference(value = "user-like")
    private List<Like> likes;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "initiated-friendship")
    private List<Friendship> initiatedFriendships;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "received-friendship")
    private List<Friendship> receivedFriendships;

    // ---Contructors---
    public User() {

    }

    // posting
    public User(String username, String email, String password, String name, String bio, String profileImg) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.profileImg = profileImg;
    }

    // getting
    public User(Integer userId, String username, String email, String password, String name, String bio,
            String profileImg, List<Post> posts, List<Comment> comments, List<Like> likes) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.profileImg = profileImg;
        this.posts = posts;
        this.comments = comments;
        this.likes = likes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Friendship> getInitiatedFriendships() {
        return initiatedFriendships;
    }

    public void setInitiatedFriendships(List<Friendship> initiatedFriendships) {
        this.initiatedFriendships = initiatedFriendships;
    }

    public List<Friendship> getReceivedFriendships() {
        return receivedFriendships;
    }

    public void setReceivedFriendships(List<Friendship> receivedFriendships) {
        this.receivedFriendships = receivedFriendships;
    }

    public void addInitiatedFriendship(Friendship friendship) {
        this.initiatedFriendships.add(friendship);
    }

    public void addReceivedFriendship(Friendship friendship) {
        this.receivedFriendships.add(friendship);
    }

    public void removeInitiatedFriendship(Friendship friendship) {
        this.initiatedFriendships.remove(friendship);
    }

    public void removeReceivedFriendship(Friendship friendship) {
        this.receivedFriendships.remove(friendship);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}
