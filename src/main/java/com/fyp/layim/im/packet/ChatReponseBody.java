package com.fyp.layim.im.packet;

import com.fyp.layim.im.common.LayimConst;

/**
 * @author fyp
 * @crate 2017/11/19 20:59
 * @project SpringBootLayIM
 * 聊天消息响应包
 */
public class ChatReponseBody extends LayimBaseBody{
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return LayimConst.CHAT_TYPE_FRIEND;
    }

    private String id;
    private String avatar;
    private String content;
    private String username;
}
