package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author fyp
 * @crate 2017/11/1 23:03
 * @project SpringBootLayIM
 */
@Entity
public class RelationShip extends DomainBase {

    public FriendGroup getFriendGroup() {
        return friendGroup;
    }

    public void setFriendGroup(FriendGroup friendGroup) {
        this.friendGroup = friendGroup;
    }

    @ManyToOne
    @JoinColumn(name = "groupid")
    private FriendGroup friendGroup;

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @OneToOne
    @JoinColumn(name="friend_uid")
    private User friend;
}
