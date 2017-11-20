package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/20 8:23
 * @project SpringBootLayIM
 */
public class ContextUser {
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    private String username;
    private String avatar;
}
