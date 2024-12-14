package com.pixel.PixelSpace.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FriendshipID")
    private Long friendshipId;

    @ManyToOne
    @JoinColumn(name = "UserID1", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "UserID2", nullable = false)
    private User user2;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    // Constructor
    public Friendship() {
    }

    // Constructor with parameters
    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.timestamp = LocalDateTime.now();
    }

    public Long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((friendshipId == null) ? 0 : friendshipId.hashCode());
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
        Friendship other = (Friendship) obj;
        if (friendshipId == null) {
            if (other.friendshipId != null)
                return false;
        } else if (!friendshipId.equals(other.friendshipId))
            return false;
        return true;
    }

}
