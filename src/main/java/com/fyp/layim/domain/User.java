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
    private String avatar;

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
        return friendGroups;
    }

    public void setFriendGroups(List<FriendGroup> friendGroups) {
        this.friendGroups = friendGroups;
    }

    public List<GroupMember> getBigGroups() {
        return bigGroups;
    }

    public void setBigGroups(List<GroupMember> bigGroups) {
        this.bigGroups = bigGroups;
    }

    @Column(name = "user_name")
    private String userName;
    private String sign;

    //一对多，一个用户有多个好友分组
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<FriendGroup> friendGroups;

    @OneToMany(mappedBy = "user")
    private List<GroupMember> bigGroups;
}


