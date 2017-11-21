package com.fyp.layim.im.packet;

/**
 * @author fyp
 * @crate 2017/11/19 20:51
 * @project SpringBootLayIM
 *  1对1，聊天消息发送包
 *  当客户端发送消息时，只需要传这两个参数即可，因为在服务端能够获取当前用户信息
 *  当前用户信息保存在 ChannelContext 中，获取方式 ContextUser user = ChannelContext.getAttribute(userId);
 *  只要知道消息接收方和消息内容，就可以转发给接收消息对象
 *  另外，单聊和群聊的区别只是msgType的不同。mstType定义在 package com.fyp.layim.im.common.intf.LayimMsgType 中
 */
public class ChatRequestBody extends LayimBaseBody {
    /**
     * 接收者用户ID 或者群ID
     * */
    private String toId;
    /**
     * 消息内容
     * */
    private String content;

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
}
