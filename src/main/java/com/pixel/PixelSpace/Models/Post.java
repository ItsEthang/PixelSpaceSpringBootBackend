package com.pixel.PixelSpace.Models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 6000)
    private String content;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Like> likes;

    public Post() {

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + postId;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
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
        Post other = (Post) obj;
        if (postId != other.postId)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

}
