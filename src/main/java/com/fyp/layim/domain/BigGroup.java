package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.*;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/1 22:59
 * @project SpringBootLayIM
 */
@Entity
public class BigGroup extends DomainBase {
    @Column(length = 20)
    private String groupName;

    @Column(length = 120)
    private String avatar;

    @Column(length = 100)
    private String description;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany
    @JoinTable(name = "user_big_group",joinColumns = {@JoinColumn(name = "group_id")},inverseJoinColumns = {@JoinColumn(name = "uid")})
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
