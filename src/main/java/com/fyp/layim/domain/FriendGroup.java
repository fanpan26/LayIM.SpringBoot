package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.management.relation.Relation;
import javax.persistence.*;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/1 22:55
 * @project SpringBootLayIM
 * 好友分组
 */
@Entity
public class FriendGroup extends DomainBase {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 20)
    private String name;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany
    @JoinTable(name = "user_friend_group",joinColumns = {@JoinColumn(name = "group_id")},inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<User> users;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name = "uid")
    private User owner;
}
