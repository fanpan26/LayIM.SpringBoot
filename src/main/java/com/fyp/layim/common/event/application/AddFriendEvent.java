package com.fyp.layim.common.event.application;

import org.springframework.context.ApplicationEvent;

/**
 * 添加好友事件
 * 已经用 guava 的 EventBus 代替
 * */
public class AddFriendEvent extends ApplicationEvent {

    public AddFriendEvent(Object source) {
        super(source);
    }

    public AddFriendEvent(Object source,long applyId) {
        super(source);
        this.applyId = applyId;
    }

    public long getApplyId() {
        return applyId;
    }

    private long applyId;
}
