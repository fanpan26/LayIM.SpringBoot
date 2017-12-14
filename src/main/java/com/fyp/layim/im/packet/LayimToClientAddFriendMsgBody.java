package com.fyp.layim.im.packet;

import com.fyp.layim.im.common.intf.LayimMsgType;

/**
 * 添加好友成功之后，需要推送给对方成功的消息
 * */
public class LayimToClientAddFriendMsgBody extends LayimBaseBody {
    @Override
    public byte getMtype() {
        return LayimMsgType.SERVER_TO_CLIENT_ADDFRIEND_NOTICE;
    }

    @Override
    public void setMtype(byte mtype) {

    }

    private String type;
    private String username;
    private String avatar;
    private long groupid;
    private long id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String sign;
}
