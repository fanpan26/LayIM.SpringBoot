package com.fyp.layim.domain.viewmodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fyp.layim.domain.User;

/**
 * @author fyp
 * @crate 2017/11/2 22:58
 * @project SpringBootLayIM
 */
public class UserViewModel extends BaseViewModel implements Comparable {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String avatar;
    private String status;
    private String sign;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.status = sort==1?"online":"offline";
        this.sort = sort;
    }

    @JsonIgnore
    private int sort;

    @Override
    public int compareTo(Object o) {
        UserViewModel o1 = (UserViewModel) o;
        return o1.getSort().compareTo(this.getSort());
    }
}
