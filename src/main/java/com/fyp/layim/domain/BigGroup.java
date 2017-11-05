package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.*;

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

    //@Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "create_uid",nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
