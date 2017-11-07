package com.fyp.layim.domain;

import com.fyp.layim.domain.base.DomainBase;

import javax.persistence.*;
import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/1 19:55
 * @project SpringBootLayIM
 * 基本用户信息
 */
@Entity
public class User extends DomainBase{

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<FriendGroup> getFriendGroups() {
        return friendGroupsOwner;
    }

    public void setFriendGroups(List<FriendGroup> friendGroups) {
        this.friendGroupsOwner = friendGroups;
    }

    public List<BigGroup> getBigGroups() {
        return bigGroupsIn;
    }

    public void setBigGroups(List<BigGroup> bigGroups) {
        this.bigGroupsIn = bigGroups;
    }

    public List<FriendGroup> getFriendGroupsIn() {
        return friendGroupsIn;
    }

    public void setFriendGroupsIn(List<FriendGroup> friendGroupsIn) {
        this.friendGroupsIn = friendGroupsIn;
    }

    private String avatar;
    private String userName;
    private String sign;

    //一对多，一个用户可以创建多个好友分组
    @OneToMany(mappedBy = "owner")
    private List<FriendGroup> friendGroupsOwner;


    //多对多，因为一个分组可以有多个用户（好友）
    @ManyToMany
    @JoinTable(name = "user_friend_group",joinColumns = {@JoinColumn(name = "uid")},inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<FriendGroup> friendGroupsIn;


    @OneToMany(mappedBy = "owner")
    private List<BigGroup> bigGroupsOwner;

    @ManyToMany
    @JoinTable(name = "user_big_group",joinColumns = {@JoinColumn(name = "uid")},inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<BigGroup> bigGroupsIn;
}


