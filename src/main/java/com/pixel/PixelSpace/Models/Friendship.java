package com.pixel.PixelSpace.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "FRIENDSHIP")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friendship_id")
    private int friendshipId;

    @Column(name = "user_id_1")
    private int userId1;

    @Column(name = "user_id_2")
    private int userId2;

    public Friendship() {

    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + friendshipId;
        result = prime * result + userId1;
        result = prime * result + userId2;
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
        if (userId1 != other.userId1)
            return false;
        if (userId2 != other.userId2)
            return false;
        return true;
    }

}
