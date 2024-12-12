package com.pixel.PixelSpace.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "FRIENDSHIP")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friendship_id")
    private int friendshipId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId1")
    private User user1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId2")
    private User user2;

    public Friendship() {

    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + friendshipId;
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
        if (friendshipId != other.friendshipId)
            return false;
        return true;
    }

}
