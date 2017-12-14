package com.fyp.layim.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * 添加好友事件
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
