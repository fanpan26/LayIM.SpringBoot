package com.fyp.layim.common.event.bus.body;

/**
 * 申请通知事件
 * tagetId为被通知人，这个通知的作用就是通过tagetId获取该id的未读消息条数，通过tio websocket 推送到前端 调用 layim.msgbox()做未读消息展示
 * */
public class ApplyEventBody  {

    public ApplyEventBody(long targetId) {
        this.targetId = targetId;
    }

    public long getTargetId() {
        return targetId;
    }
    private long targetId;

}
