package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/19 20:51
 * @project SpringBootLayIM
 * 1对1，聊天消息发送包
 */
public class ChatRequestBody extends LayimBaseBody {
    /**
     * 接收者用户ID
     * */
    private String toId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 消息内容
     * */
    private String content;
}
