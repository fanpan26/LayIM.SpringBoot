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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "uid",nullable = false)
    private User user;

    public List<RelationShip> getRelationShips() {
        return relationShips;
    }

    public void setRelationShips(List<RelationShip> relationShips) {
        this.relationShips = relationShips;
    }

    @OneToMany(mappedBy = "friendGroup")
    private List<RelationShip> relationShips;
}
