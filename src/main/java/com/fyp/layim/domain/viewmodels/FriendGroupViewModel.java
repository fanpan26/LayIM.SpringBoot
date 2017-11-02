package com.fyp.layim.domain.viewmodels;

import com.fyp.layim.domain.User;

import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/2 21:51
 * @project SpringBootLayIM
 */
public class FriendGroupViewModel extends BaseViewModel{
    private String groupname;
    private Integer online;
    private List<UserViewModel> list;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public List<UserViewModel> getList() {
        return list;
    }

    public void setList(List<UserViewModel> list) {
        this.list = list;
    }
}
