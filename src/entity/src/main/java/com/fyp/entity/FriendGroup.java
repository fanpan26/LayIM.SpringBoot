package com.fyp.entity;

import java.util.List;

public class FriendGroup {

    public FriendGroup(){
        setOnline(0);
    }

    public String getGroupname() {
        return groupname;
    }
    public void setGroupname(String name) {
        this.groupname = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    private Integer online;
    private List<User> list;
    private String groupname;
    private Long id;
}
