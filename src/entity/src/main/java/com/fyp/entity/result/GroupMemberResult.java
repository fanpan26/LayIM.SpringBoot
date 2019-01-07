package com.fyp.entity.result;

import com.fyp.entity.User;

import java.util.List;

public class GroupMemberResult {
    private int members;

    public int getMembers() {
        return members;
    }

    private List<User> list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
        this.members = list.size();
    }

    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
}
