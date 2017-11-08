package com.fyp.layim.domain.viewmodels;

import java.util.List;

/**
 * @author fyp
 * @crate 2017/11/8 23:26
 * @project SpringBootLayIM
 */
public class LayimGroupMembersViewModel {
    private UserViewModel owner;
    private List<UserViewModel> list;

    public UserViewModel getOwner() {
        return owner;
    }

    public void setOwner(UserViewModel owner) {
        this.owner = owner;
    }

    public List<UserViewModel> getList() {
        return list;
    }

    public void setList(List<UserViewModel> list) {
        this.list = list;
    }

    public int getMembers() {
        return list.size();
    }

    private int members;
}
